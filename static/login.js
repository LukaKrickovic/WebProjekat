let emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
let passwordRegex = new RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/);
let usernameRegex = new RegExp(/^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/);

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
		rightPanel: false
	},

	mounted() {

	},

	methods: {
    	login : function() {
			axios.
				post('/login', this.username, this.password, this.role.toUpperCase())
				.then(response => alert(this.username + this.password + this.role.toUpperCase()));
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
			axios.
				post('/register', this.username, this.password, this.role.toUpperCase())
		}
    },
	filters: {

	}
});