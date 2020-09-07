let emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
let passwordRegex = new RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/);
let usernameRegex = new RegExp(/^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/);
const instance = axios.create({baseURL: 'http://localhost:8080'})

var app = new Vue({
	el: '#container',

	data: {
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
	},

	mounted() {

	},

	methods: {
    	login : function() {
			instance.
				post('/login', {}, { params: {
					username: this.username,
					password: this.password,
					role: this.role
				}})
				.then(response => {
					if(response.data.localeCompare("ok") === 0){
					window.location.replace("/index.html");
				} else if(response.data.localeCompare("failed") === 0){
					alert("Username or password are incorrect!");
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

			if(!usernameRegex.test(String(this.usernameRegistration))){
				alert("Username is invalid!");
				return;
			}

			if(!passwordRegex.test(String(this.passwordRegistration))){
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
			instance.
				post('/register', {}, { params: {
					role: this.roleRegistration,
					name: this.name,
					surname: this.surname,
					gender: this.gender,
					username: this.usernameRegistration,
					password: this.passwordRegistration
				}})
				.then(response => {
					if(response.data.localeCompare("ok") === 0){
					window.location.replace("/index.html");
				} else if(response.data.localeCompare("failed") === 0){
					alert("Credentials are not valid or username is already in use!");
				}
			})
				.catch(err => console.error(err));
		}
    },
	filters: {

	}
});