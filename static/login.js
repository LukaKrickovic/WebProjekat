//let emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
//let passwordRegex = new RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/);
//let usernameRegex = new RegExp(/^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/);
//const instance = axios.create({baseURL: 'http://localhost:8080'});

Vue.component("login",{ 
    data: function(){
		return{
			username: "",
			password: "",
			role: "Guest",
			roleRegistration: "Guest",
			name: "",
			surname: "",
			email: "",
			emailRegistration: "",
			usernameRegistration: "",
			passwordRegistration: "",
			gender: "",
			errorSpan: "",
			rightPanel: false
		}
	},
    
	template: `
	<div class="super-container">
    <div class="login-container" id="container" v-bind:class="{ rightpanelactive: rightPanel }">
        <div class="form-container sign-up-container">
        
        <div class="form">
            <h1 class="login-h1">Register</h1>
            <input type="text" name="name" placeholder="Name" v-model="name">
            <input type="text" name="surname" placeholder="Surname" v-model="surname">
            <label id="genderLabel">Gender:</label>
            <select name="gender" v-model="gender">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="NONBINARY">Non binary</option>
              </select>
            <input type="email" name="email" placeholder="Email" v-model="emailRegistration">
            <input type="username" name="username" placeholder="Username" v-model="usernameRegistration">
            <input type="password" name="password" placeholder="Password" v-model="passwordRegistration">
            <button v-on:click="attemptToRegister">Register</button>
        </div>
        </div>
        <div class="form-container sign-in-container" >
            <div class="form">
            <h1>Log in</h1>
            <input type="text" name="username" placeholder="Username" v-model="username">
            <input type="password" name="password" placeholder="Password" v-model="password">
            <button v-on:click="login">Log in</button>
        </div>
        </div>
        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1 class="login-h1">Welcome Back!</h1>
                    <p class="login-p">To keep connected with us please login with your personal info.</p>
                    <button class="ghost" id="signIn" v-on:click="shiftPanels">Log in</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1>Hello!</h1>
                    <p>Enter your details and start your journey with us!</p>
                    <button class="ghost" id="signUp" v-on:click="shiftPanels()">Register</button>
                </div>
            </div>
        </div>
		</div>
		</div>
    `,

	methods: {
    	login : function() {
			axios.
				post('/rest/login', {}, { params: {
					username: this.username,
					password: this.password
				}})
				.then(response => {
					if(response.data.name !== null){
					this.user = response.data;
					window.localStorage.setItem('jwt', this.user.JWTToken);
					window.location.replace('#/home');
					} else {
						alert("Incorrect username / password");
					}
				})
				.catch(err => console.error(err));
		},
    	registerLog : function(usernameInput, passwordInput) {
			alert("Logujem se posle registracije");
			axios.
				post('/rest/login', {}, { params: {
					username: usernameInput,
					password: passwordInput
				}})
				.then(response => {
					if(response.data.name !== ""){
					this.user = response.data;
					window.localStorage.setItem('jwt', this.user.JWTToken);
					window.location.replace('#/home');
					} else {
						alert("Incorrect username / password");
					}
				})
				.catch(err => console.error(err));
		},

		shiftPanels : function(){
			this.rightPanel = !this.rightPanel;
		},

		attemptToRegister : function(){
			/*if(!emailRegex.test(String(this.emailRegistration))){
				alert("Email is invalid!");
				return;
			}*/

			if(!/^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/.test(String(this.usernameRegistration))){
				alert("Username is invalid!");
				return;
			}

			if(!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/.test(String(this.passwordRegistration))){
				alert("Password is invalid!");
				return;
			}

			if(this.usernameRegistration === "" || this.roleRegistration === "" || this.name === "" || this.surname === "" || this.emailRegistration === ""){
				alert("Please fill all fields!");
				return;
			}

			this.register();
		},

		register : function(){
			alert("Registrujem se");
			axios.
				post('/rest/register', {}, { params: {
					role: "GUEST",
					name: this.name,
					surname: this.surname,
					gender: this.gender,
					username: this.usernameRegistration,
					password: this.passwordRegistration
				}})
				.then(response => {
					if(response.data.localeCompare("ok") === 0){
					this.registerLog(this.usernameRegistration, this.passwordRegistration);
					window.location.replace("/#/");
				} else if(response.data.localeCompare("failed") === 0){
					alert("Credentials are not valid or username is already in use!");
				}
			})
				.catch(err => console.error(err));
		}
    }
});