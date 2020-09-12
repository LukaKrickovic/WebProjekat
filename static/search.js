const instance = axios.create({baseURL: 'http://localhost:8080'});

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
        empty: true,
        destination: "",
        searchResults: []
    },

    mounted(){
 		instance
			.get('/startup-index')
			.then(res => {
					if(res.data.localeCompare('nouser') !== 0){
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


            instance
            .get('/do-results-exist')
            .then(res => {
                if(res.data.localeCompare("noresults") !== 0){
                    this.empty = false;
                    alert('Lista je prazna: ' + this.empty);
                    instance
                        .get('/read-form-results')
                        .then(res => {
                            this.searchResults = res.data;
                            if(searchResults !== null){
                                this.destination = this.searchResults[0];
                            }
                    });
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

    }
});