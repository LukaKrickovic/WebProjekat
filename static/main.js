const instance = axios.create({baseURL: 'http://localhost:8080'})

//Selectors
let header = document.querySelector('.header');
let hamburgerMenu = document.querySelector('.hamburger-menu');

window.addEventListener('scroll', function(){
    let windowPosition = window.scrollY > 0;
    header.classList.toggle('active', windowPosition)
})

hamburgerMenu.addEventListener('click', function(){
    header.classList.toggle('menu-open');
})

var app = new Vue({
	el: '#container',

	data: {
		user: null,
		displayName: "",
		burgerActive: false
	},

	mounted() {
		instance
			.get('/index-user')
			.then(res => {
					if(res.data !== null){
						this.user = res.data;
					}
					alert(this.user.role);
					this.displayName = this.user.name;
				});
	},

	methods: {
		activateTheBurger : function(){
			this.burgerActive = !this.burgerActive;
		}
	},
	filters: {

	}
});