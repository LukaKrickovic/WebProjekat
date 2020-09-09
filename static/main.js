const instance = axios.create({baseURL: 'http://localhost:8080'});

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
		activateTheBurger: false,
		hideFromGuest: false
	},

	mounted() {
		instance
			.get('/startup-index')
			.then(res => {
					if(res.data.localeCompare('nouser') === 1){
						instance
							.get('/startup-user')
							.then(res => {
								this.user = res.data;
								this.displayName = this.user.name;
								if(this.user.role.localeCompare('GUEST') === 0){
									this.hideFromGuest = true;
								}
							});
					} else {
						alert(res.data);
					}

					
				});
	},

	methods: {
		burgerActive : function(){
			
		},

		logout : function(){
			instance
				.get('/logout')
				.then(res => {
					alert(res.data);
					if(res.data){
						window.location.replace("/index.html");
					}
				});
		}
	},
	filters: {

	}
});