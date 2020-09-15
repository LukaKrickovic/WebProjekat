Vue.component("search",{ 
    components: {
        VueGallerySlideshow
      },
    data: function(){
        return {
		user: null,
		displayName: "",
		activateTheBurger: false,
        hideFromGuest: false,
        empty: true,
        destination: "",
        expanded: null,
        bookingDialogOpen: false,
        seletedUnit: null,
        bookingName: "",
        bookingSurname: "",
        bookingEmail: "",
        bookingAdults: "",
        bookingChildren: "",
        checkInParam: "",
        checkOutParam: "",
        destinationParam: "",
        adultCountParam: "",
        childrenCountParam: "",
        bookingMessage: "",
        displayImages: [],
        index: null,
        imagesDialogOpen: false,
        searchResults: []
        }
    },
    
    template: `
    <div id="result-container">
        <header class="header">
            <div class="container">
                <nav class="nav">
                    <a href="index.html" class="logo">
                        <img src="./images/logo.png" alt="">
                    </a>
    
                    <div class="hamburger-menu" v-on:click="activateTheBurger" v-bind:class="{ active: burgerActive }">
                        <i class="fas fa-bars"></i>
                        <i class="fas fa-times"></i>
                    </div>
    
                    <ul class="nav-list">
                        <li class="nav-item">
                            <a href="index.html" class="nav-link">Home</a>
                        </li>
    
                        <li class="nav-item">
                            <a href="#" class="nav-link">About</a>
                        </li>
    
                        <li class="nav-item">
                            <a href="#" class="nav-link">Offers</a>
                        </li>
                        
                        <li class="nav-item" v-bind:class="{ hidden: user !== null }">
                            <a href="login.html" class="nav-link">Login/Register</a>
                        </li>
                    </ul>
    
                    <div class="dropdown-user" v-bind:class="{ hidden: user === null }">
                        <button class="user-button">
                            {{ displayName }}
                            <div class="user-dropdown-content">
                                <a href="#" class="dropdown-link">Manage your account information</a>
                                <a href="#" class="dropdown-link">Upcoming reservations</a>
                                <a href="#" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Search for users</a>
                                <a href="#" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Add a unit</a>
                                <a href="index.html" class="dropdown-link" v-on:click="logout">Logout</a>
                            </div>
                        </button>
                </div>
                </nav>
            </div>
        </header>

        <main>
            <div class="list" v-if="empty === false">
                <div class="head">
                    <span>{{searchResults.length}} units have been found</span>
                    <h2>Stays in {{searchResults[0].location.address.city}}</h2>
                </div>
                <ul>
                    
                    <li v-for="item in searchResults"><div class="search-result">
                        <hr>
                        <div id="bookingModalDialog" class="modal-booking" v-if="bookingDialogOpen === true">

                        <!-- Modal content -->
                        <div class="modal-booking-content">
                        <button class="close-x" v-on:click="closeBookingDialog">   
                            <span class="close-booking">&times;</span>
                        </button>
                        <h2 class="modal-headers">Please confirm your details!</h2>
                        <h4 class="modal-headers">Booking confirmation for {{selectedUnit.name}}</h4>
                        <input type="text" name="name" placeholder="Name" v-model="bookingName">
                        <input type="text" name="surname" placeholder="Surname" v-model="bookingSurname">
                        <input type="email" name="email" placeholder="Email" v-model="bookingEmail">
                        <input type="number" name="adultCount" placeholder="Adults" v-model="bookingAdults">
                        <input type="number" name="childrenCount" placeholder="Children" v-model="bookingChildren">
                        <input type="text" name="message" placeholder="Message" v-model="bookingMessage">
                        <button v-on:click="confirmBooking">Confirm</button>
                        </div>

                        </div>

                        <div id="imagesModalDialog" class="modal-booking" v-if="imagesDialogOpen === true">

                        <!-- Modal content -->
                        <div class="modal-booking-content">
                        <button class="close-x" v-on:click="closeImagesDialog">   
                            <span class="close-booking">&times;</span>
                        </button>
                        <h2 class="modal-headers">Images for {{selectedUnit.name}}</h2>
                        
                        <img v-for="(image, i) in displayImages" :src="image" :key="i" @click="index = i">
                        <vue-gallery-slideshow :images="displayImages" :index="index" @close="index = null"></vue-gallery-slideshow>
                        </div>

                        </div>


                        <img v-bind:src="item.imageSources[0]" width="200px" v-if="item.imageSources !== null"></img>
                        <br>
                        <span class="roomType">{{item.roomType}}</span><br>
                        <span class="roomName">{{item.name}}</span><br>
                        <span class="roomGuests"><em>{{item.numOfGuests}}</em> guests - <em>{{item.numOfRooms}}</em> rooms</span><br>
                        <div v-if="expanded === item.id" class="expanded-data">
                            <span class="address"> Address: {{item.location.address.street}} {{item.location.address.number}}, {{item.location.address.city}} ({{item.location.address.zipCode}}) </span>
                            <br>
                            <ul class="amenity-list">
                                <li v-for="amenity in item.amenities">
                                    <span>{{amenity.description}} &#10003;</span>
                                </li>
                            </ul>
                            <br>
                            <button class="additional-info-button" v-on:click="makeBooking(item)">Book now!</button>
                            <br>
                            <button class="additional-info-button" v-on:click="showImages(item)">Show images</button>
                            <br>
                            <button class="additional-info-button" v-on:click="hide">Hide</button>
                            <br>
                        </div>
                        <span class="pricePerNight"><b>{{item.pricePerNight}}$</b>/Night</span>
                        <br>
                        <button class="expand-info-button" v-if="expanded !== item.id" v-on:click="expand(item.id)">Show more</button>
                        
                    </div></li>
                </ul>
            </div>
            <div v-else>
            <h2>Oops! No stays found for the given parameters</h2>
            </div>

        </main>
        <footer class="footer">
            <div class="container">
                <div class="footer-content">
                    <!-- <div class="footer-content-brand">
                        <a href="index.html" class="logo">
                            <img src="./images/logo.png" class="logo-image" alt="">
                        </a>
                        <div class="paragraph">
                            Lorem ipsum dolor sit amet consectetur adipisicing 
                        </div> 
                    </div> -->
                    <div class="social-media-wrap">
                        <h4 class="footer-heading">Follow us</h4>
                        <div class="social-media">
                            <a href="#" class="sm-link"><i class="fab fa-twitter"></i></a>
                            <a href="#" class="sm-link"><i class="fab fa-facebook-square"></i></a>
                            <a href="#" class="sm-link"><i class="fab fa-instagram"></i></a>
                            <a href="#" class="sm-link"><i class="fab fa-pinterest"></i></a>
                            <a href="#" class="sm-link"><i class="fab fa-tripadvisor"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    </div>
    `,

    mounted(){
        var jwt = window.localStorage.getItem('jwt');
        if(jwt){
            axios
            .get('/rest/getLoggedInUser', { params:
                { 
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                    this.user = res.data;
                    if(this.user !== null){
                        this.displayName = this.user.name;
                        if(this.user.role.localeCompare('GUEST') === 0){
                            this.hideFromGuest = true;
                        }
                    }
                    alert(this.user);
                });
        }

        this.checkInParam = this.$route.query.checkIn;
        this.checkOutParam = this.$route.query.checkOut;
        this.destinationParam = this.$route.query.destination;
        this.adultCountParam = this.$route.query.adultCount;
        this.childrenCountParam = this.$route.query.childrenCount;
        //alert(checkInParam + " " + checkOutParam + " " + destinationParam + " " + adultCountParam + " " + childrenCountParam);
        axios
        .get('/rest/search-units', {params: {
            checkIn: this.checkInParam,
            checkOut: this.checkOutParam,
            destination: this.destinationParam,
            adultCount: this.adultCountParam,
            childrenCount: this.childrenCountParam
        }})
        .then(res => {
            this.searchResults = res.data;
            if(this.searchResults !== null){
                if(this.searchResults.length > 0){
                    this.empty = false;
                }
            }
        })
            
    },

    methods: {

        /*showImages: function(item){
            if(item.imageSources !== null){
                if(item.imageSources.length > 0){
                    for(img in item.imageSources){
                        axios
                            .get('/rest/get-image', { params:{
                                imgPath = img
                            } })
                            .then(res => {
                                if(res.data != null){
                                    this.displayImages.push(res.data);
                                }
                            })  
                    }
            }
        }
        },*/

        showImages: function(item){
            if(item.imageSources !== null){
                if(item.imageSources.length > 0){
                    this.selectedUnit = item;
                    this.displayImages = item.imageSources;
                    this.imagesDialogOpen = true;
                }
            }
        },

        closeImagesDialog: function(){
            this.imagesDialogOpen = false;
            this.displayImages = [];
        },

        confirmBooking: function(){
            if(this.user !== null){
            axios
            .post('/rest/make-reservation', {}, { params: {
                user: this.user,
                unit: this.selectedUnit,
                checkIn: this.checkInParam,
                checkOut: this.checkOutParam,
                pricePerNight: this.selectedUnit.pricePerNight,
                message: this.bookingMessage
            } })
            .then(res => {
                if(res.data === true){
                    alert("Booking for " + this.selectedUnit.name + " was successful!");
                    window.location.replace('index.html');
                } else {
                    alert("Failed to book " + this.selectedUnit.name + "!");
                }
            });
        }
        else {
            alert("Please log in first!");
        }
            
        } ,

        makeBooking: function(unit){
            if(window.localStorage.getItem('jwt')){
                this.bookingDialogOpen = true;
                this.selectedUnit = unit;
                this.bookingName = this.user.name;
                this.bookingSurname = this.user.surname;
                this.bookingAdults = this.adultCountParam;
                this.bookingChildren = this.childrenCountParam;
            }
        },
        closeBookingDialog: function(){
            this.bookingDialogOpen = false;
        },

        hide: function(){
            this.expanded = null;
        },

        expand : function(id){
            this.expanded = id;
        },


        burgerActive : function(){
			
        },

        

		logout : function(){
            alert("LOGOUT");
            var jwt = window.localStorage.getItem('jwt');
            alert(jwt);
            if(jwt){
                axios
                .get('/rest/logout', { params: {
                    Auth: 'Sender: ' + jwt
                }})
                .then(res => {
                    if(res.data === true){
                        this.user = null;
                        window.localStorage.removeItem('jwt');
                        alert("Successfully logged out");
                    }
                });
            }
        },
    }
});