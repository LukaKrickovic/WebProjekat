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
		hideFromGuest: false,
		destination: "",
		checkIn: "",
		checkOut: "",
		adultCount: "",
		childrenCount: ""
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
						location.replace("/index.html");
					}
				});
		},

		searchUnits : function(){
			alert(this.adultCount + " " + this.childrenCount);
			alert(this.destination);

			if(this.childrenCount === null){
				this.childrenCount = 0;
			}
			if(this.adultCount === null){
				this.adultCount = 0;
			}

			if(this.checkIn !== null && this.checkOut !== null){
				var checkInDate = new Date(this.checkIn);
				var checkOutDate = new Date(this.checkOut);
				if(checkInDate.getTime() < checkOutDate.getTime()){
					alert("Set dates correctly!")
					return;
				}

				if(checkInDate.getTime() < Date.now){
					alert("Set dates correctly!")
					return;
				}

				if(checkOutDate.getTime() < Date.now){
					alert("Set dates correctly!")
					return;
				}
			}

			instance
				.get('/book-form', { params: {
					destination: this.destination,
					checkIn: this.checkIn,
					checkOut: this.checkOut,
					adults: this.adultCount,
					children: this.childrenCount
				}})
				.then(res => {
					alert(res.data);
					
					if(res.data.localeCompare("ok") === 0){
						location.replace("/search-results.html");
					}
					
				});

		}
	},
	filters: {

	}
});
