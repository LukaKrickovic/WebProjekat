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
        bottomPrice: 0,
        topPrice: 0,
        bookingDialogOpen: false,
        seletedUnit: null,
        bottomRC: 0,
        topRC: 0,
        bookingName: "",
        filtersHidden: true,
        bookingSurname: "",
        bookingEmail: "",
        bookingAdults: "",
        bookingChildren: "",
        checkInParam: "",
        mapDialogOpen: false,
        checkOutParam: "",
        destinationParam: "",
        adultCountParam: "",
        childrenCountParam: "",
        bookingMessage: "",
        displayImages: [],
        index: null,
        imagesDialogOpen: false,
        airConditioningChecked: false,
        swimmingPoolChecked: false,
        freeParkingChecked: false,
        wifiChecked: false,
        roomTypeFilter: "",
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
                            <router-link to="/bookings" class="dropdown-link">Bookings</router-link>
                            <a href="#" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Search for users</a>
                            <router-link to="/units" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Units</router-link>
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
                    <br><br>
                </div>
                <div>
                    
                        <div v-if="filtersHidden === false">
                        <label>Sorts:</label>
                        <ul>
                        <li><button v-on:click="sortByPriceAsc">Price descending</button></li><br>
                        <li><button v-on:click="sortByPriceDesc">Price ascending</button></li><br>
                        </ul>
                        <label>Filters:</label>
                        <ul>
                        <li><select v-model="roomTypeFilter">
                            <option value="ROOM">Room</option>
                            <option value="APARTMENT">Apartment</option>
                        </select></li>
                        <li><input type="checkbox" v-model="airConditioningChecked">Air conditioning</input></li>
                        <li><input type="checkbox" v-model="swimmingPoolChecked">Swimming pool</input></li>
                        <li><input type="checkbox" v-model="freeParkingChecked">Free parking</input></li>
                        <li><input type="checkbox" v-model="wifiChecked">Free wifi</input></li>
                        </ul><br>
                        <label>Bottom and top price ($):</label>
                        <ul>
                        <li><input type="number" width="100px" placeholder="Bottom price" v-model="bottomPrice"></input></li>
                        <li><input type="number" width="100px" placeholder="Top price" v-model="topPrice"></input></li>
                        <br><br>
                        <label>Bottom and top room count:</label>
                        <li><input type="number" width="100px" placeholder="Bottom room count" v-model="bottomRC"></input></li>
                        <li><input type="number" width="100px" placeholder="Top room count" v-model="topRC"></input></li>
                        <button v-on:click="applyFilters">Apply filters</button>
                        <button v-on:click="hideFilters">Hide filters</button>
                        </ul>
                        <br>
                        </div>
                        <div v-else>
                            <button v-on:click="showFilters">Show filters</button>
                        </div>
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
                        
                        <div id="mapModalDialog" class="modal-booking" v-if="mapDialogOpen === true">

                        <!-- Modal content -->
                        <div class="modal-booking-content">
                        <button class="close-x" v-on:click="closeMapDialog">   
                            <span class="close-booking">&times;</span>
                        </button>
                        <h2 class="modal-headers">Map for {{selectedUnit.name}}</h2>
                        <div id="map" class="map">

                        </div>
                        
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
                            <button class="additional-info-button" v-on:click="showMapDialog(item)">Show on map</button>
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

        sortByPriceAsc: function(){
            this.searchResults.sort(function (result1, result2) {

                // Sort by price
                // If the first item has a higher number, move it down
                // If the first item has a lower number, move it up
                if (parseFloat(result1.pricePerNight) > parseFloat(result2.pricePerNight)) return -1;
                if (parseFloat(result1.pricePerNight) < parseFloat(result2.pricePerNight)) return 1;
            
            });
        },

        showMapDialog: function(item){
            this.mapDialogOpen = true;
            this.selectedUnit = item;
            var map = new ol.Map({
                target: 'map',
                layers: [
                  new ol.layer.Tile({
                    source: new ol.source.OSM()
                  })
                ],
                view: new ol.View({
                  center: ol.proj.fromLonLat([parseFloat(item.location.longitude), parseFloat(item.location.latitude)]),
                  zoom: 4
                })
              });
        },

        closeMapDialog: function(){
            this.mapDialogOpen = false;
        },

        showFilters: function(){
            this.filtersHidden = false;
        },
        hideFilters: function(){
            this.filtersHidden = true;
        },

        sortByPriceDesc: function(){
            this.searchResults.sort(function (result1, result2) {

                // Sort by price
                // If the first item has a higher number, move it down
                // If the first item has a lower number, move it up
                if (parseFloat(result1.pricePerNight) < parseFloat(result2.pricePerNight)) return -1;
                if (parseFloat(result1.pricePerNight) > parseFloat(result2.pricePerNight)) return 1;
            
            });
        },

/*
        unitHasAC(unit){
            for(amenity in unit.amenities){
                if(amenity.description.trim().toLowerCase() == 'air conditioning'){
                    return true;
                }
            }

            return false;
        },
        
        unitHasSwimmingPool(unit){
            for(amenity in unit.amenities){
                if(amenity.description.trim().toLowerCase() == 'swimming pool'){
                    return true;
                }
            }

            return false;
        },
        
        unitHasFreeParking(unit){
            for(amenity in unit.amenities){
                if(amenity.description.trim().toLowerCase() == 'free parking'){
                    return true;
                }
            }

            return false;
        },
        
        unitHasWifi: function(unit){
            for(amenity in unit.amenities){
                if(amenity.description.trim().toLowerCase() == 'free wifi'){
                    return true;
                }
            }

            return false;
        },*/

        applyFilters: function(){
            var backup = this.searchResults;
            var backup2 = this.searchResults;
 /*           if(this.airConditioningChecked === true){
                for(unit in backup){
                    if(!this.unitHasAC(unit)){
                        backup2.removeItem(unit);
                    }
                }
            }

            backup = backup2;
            if(this.freeParkingChecked === true){
                for(unit in backup){
                    if(!this.unitHasFreeParking(unit)){
                        backup2.removeItem(unit);
                    }
                }
            }
            backup = backup2;
            if(this.swimmingPoolChecked === true){
                for(unit in backup){
                    if(!this.unitHasSwimmingPool(unit)){
                        backup2.removeItem(unit);
                    }
                }
            }
            backup = backup2;
            if(this.wifiChecked === true){
                for(unit in backup){
                    if(!this.unitHasWifi(unit)){
                        backup2.removeItem(unit);
                    }
                }
            }

            if(this.wifiChecked){
                backup = backup.filter(function(value, index, arr){return this.unitHasWifi(value);});
            }

            if(this.swimmingPoolChecked){
                backup = backup.filter(function(value, index, arr){return this.unitHasSwimmingPool(value);});
            }

            if(this.freeParkingChecked){
                backup = backup.filter(function(value, index, arr){return this.unitHasFreeParking(value);});
            }

            if(this.airConditioningChecked){
                backup = backup.filter(function(value, index, arr){return this.unitHasAC(value);});
            }

            this.searchResults = backup;
            */
           var wifi = "";
           var airConditioning = "";
           var freeParking = "";
           var swimmingPool = "";
           if(this.wifiChecked){
               wifi="YES";
           }
           if(this.swimmingPoolChecked){
               swimmingPool="YES";
           }
           if(this.freeParkingChecked){
               freeParking="YES";
           }
           if(this.airConditioningChecked){
               airConditioning="YES";
           }
           alert(this.searchResults.length);

           axios
           .get('/rest/filter-by-criteria', {params:{
               wifi: wifi,
               airConditioning: airConditioning,
               freeParking: freeParking,
               swimmingPool: swimmingPool,
               searchResults: JSON.stringify(this.searchResults),
               roomType: this.roomTypeFilter,
               bottomPrice: this.bottomPrice,
               topPrice: this.topPrice,
               bottomRC: this.bottomRC,
               topRC: this.topRC
           }})
           .then(res => {
                if(res.data !== null){
                    if(res.data.length > 0){
                        this.searchResults = res.data;
                    }
                }
           });
        },

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