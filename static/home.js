Vue.component("home",{ 
   
        data: function(){
            return{
            user: null,
            displayName: "",
            activateTheBurger: false,
            hideFromGuest: false,
            destination: "",
            checkIn: "",
            checkOut: "",
            adultCount: "",
            bookingDialogOpen: false,
            upcomingBookings: [],
            noBookings: true,
            childrenCount: "",

            editDialogOpen: false,
            newName: "",
            newSurname: "",
            currentPassword: "",
            newPassword: "",
            confPassword: ""
            }
        },

        template: `
    <div id="container">
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
                        <router-link to="/login" class="nav-link">Login/Register</router-link>
                    </li>
                </ul>

                <div class="dropdown-user" v-bind:class="{ hidden: user === null }">
                    <button class="user-button">
                        {{ displayName }}
                        <div class="user-dropdown-content">
                            <button v-on:click="openEditingDialog" class="dropdown-link">Edit your account information</button>
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

    <div id="editModalDialog" class="modal-booking" v-if="editDialogOpen === true">

    <!-- Modal content -->
    <div class="modal-booking-content">
    <button class="close-x" v-on:click="closeEditingDialog">   
        <span class="close-booking">&times;</span>
    </button>
    <h2 class="modal-headers">Edit your information here!</h2>
    <h4 class="modal-headers"><span v-if="user.gender == 'MALE'">MR</span><span v-if="user.gender == 'FEMALE'">MRS</span><span v-if="user.gender == 'NONBINARY'">MR/MRS</span>. {{user.name}}</h4>
    <br><br>
    <label>Name:</label>
    <input type="text" name="name" placeholder="Name" v-model="newName">
    <label>Surname:</label>
    <input type="text" name="surname" placeholder="Surname" v-model="newSurname">
    <label>Current password:</label>
    <input type="password" name="currentPassword" placeholder="Current password" v-model="currentPassword">
    <label>New password:</label>
    <input type="password" name="newPassword" placeholder="New password" v-model="newPassword">
    <label>Confirm new password:</label>
    <input type="password" name="confPassword" placeholder="Confirm password" v-model="confPassword">
    
    <br><br>
    <button v-on:click="editUser">Confirm</button>
    </div>


    </div>

        <div class="hero">
            <div class="container">
                <div class="main-heading">
                    <h1 class="title">Discover</h1>
                    <h2 class="subtitle">Luxury hotels</h2>
                </div>
                <a href="#" class="btn btn-gradient">Explore now
                    <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                </a>
            </div>
        </div>

        <section class="booking">
            <div class="container">

                    <div class="input-group">
                        <label class="input-label" for="destination">Destination</label>
                        <input type="text" class="input" id="destination" v-model="destination">
                    </div>
                    <div class="input-group">
                        <label class="input-label" for="check-in">Check-in</label>
                        <input type="date" class="input" id="check-in" v-model="checkIn">
                    </div>
                    <div class="input-group">
                        <label class="input-label" for="check-out">Check-out</label>
                        <input type="date" class="input" id="check-out" v-model="checkOut">
                    </div>
                    <div class="input-group">
                        <label class="input-label" for="adults">Adults</label> 
                        <select class="options" id="adults" v-model="adultCount">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div class="input-group">
                        <label class="input-label" for="children">Children</label> 
                        <select class="options" id="children" v-model="childrenCount">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    
                        <button class="btn form-btn btn-blue" v-on:click="searchUnits">Search
                            <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                        </button>
                    

            </div>
        </section>

        <section class="hotels">
            <div class="container">
                <h5 class="section-head">
                    <span class="heading">Explore</span>
                    <span class="sub-heading">Our beatiful hotels</span>
                </h5>
                <div class="grid">
                    <div class="grid-item featured-hotels">
                        <img src="./images/hotel_astro_resort.jpg" alt="" class="hotel-image">
                        <h5 class="hotel-name">Astro Hotel</h5>
                        <span class="hotel-price">From $200/Night</span>
                        <div class="hotel-rating">
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star-half rating"></i>
                        </div>
                        <a href="#" class="btn btn-gradient">Book now
                            <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                        </a>
                    </div>

                    <div class="grid-item featured-hotels">
                        <img src="./images/hotel_the_enchanted_garden.jpg" alt="" class="hotel-image">
                        <h5 class="hotel-name">Enchanted Gardem</h5>
                        <span class="hotel-price">From $300/Night</span>
                        <div class="hotel-rating">
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                        </div>
                        <a href="#" class="btn btn-gradient">Book now
                            <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                        </a>
                    </div>

                    <div class="grid-item featured-hotels">
                        <img src="./images/hotel_the_paradise.jpg" alt="" class="hotel-image">
                        <h5 class="hotel-name">The Paradise</h5>
                        <span class="hotel-price">From $350/Night</span>
                        <div class="hotel-rating">
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star rating"></i>
                            <i class="fas fa-star-half rating"></i>
                        </div>
                        <a href="#" class="btn btn-gradient">Book now
                            <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <!-- <section class="offer">
            <div class="container">
                <div class="offer-content">
                    <div class="discount">
                        40% off
                    </div>
                    <h5 class="hotel-name">The Paradise</h5>
                    <div class="hotel-rating">
                        <i class="fas fa-star rating"></i>
                        <i class="fas fa-star rating"></i>
                        <i class="fas fa-star rating"></i>
                        <i class="fas fa-star rating"></i>
                        <i class="fas fa-star-half rating"></i>
                    </div>
                    <p class="paragraph">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Eius nesciunt, 
                        magnam saepe magni, fugiat id architecto nam explicabo illo harum, totam distinctio.
                    </p>
                    <a href="#" class="btn btn-gradient">Redeem offer
                        <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                    </a>
                </div>
            </div>
        </section> -->

        <section class="rooms">
            <div class="container">
                <h5 class="section-head">
                    <span class="heading">Luxurious</span>
                    <span class="sub-heading">Affordable rooms</span>
                </h5>
                <div class="grid rooms-grid">
                    <div class="grid-item featured-rooms">
                        <div class="image-wrap">
                            <img src="./images/room_1.jpg" alt="" class="room-image">
                            <h5 class="room-name">Astro Hotel</h5>
                        </div>
                        <div class="room-info-wrap">
                            <span class="room-price">$200 <span class="per-night">Per night</span></span>
                            <p class="paragraph">
                                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            </p>
                            <a href="#" class="btn rooms-btn">Book now &rarr;</a>
                        </div>
                    </div>

                    <div class="grid-item featured-rooms">
                        <div class="image-wrap">
                            <img src="./images/room_2.jpg" alt="" class="room-image">
                            <h5 class="room-name">Astro Hotel</h5>
                        </div>
                        <div class="room-info-wrap">
                            <span class="room-price">$200 <span class="per-night">Per night</span></span>
                            <p class="paragraph">
                                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            </p>
                            <a href="#" class="btn rooms-btn">Book now &rarr;</a>
                        </div>
                    </div>

                    <div class="grid-item featured-rooms">
                        <div class="image-wrap">
                            <img src="./images/room_3.jpg" alt="" class="room-image">
                            <h5 class="room-name">Astro Hotel</h5>
                        </div>
                        <div class="room-info-wrap">
                            <span class="room-price">$200 <span class="per-night">Per night</span></span>
                            <p class="paragraph">
                                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            </p>
                            <a href="#" class="btn rooms-btn">Book now &rarr;</a>
                        </div>
                    </div>

                    <div class="grid-item featured-rooms">
                        <div class="image-wrap">
                            <img src="./images/room_4.jpg" alt="" class="room-image">
                            <h5 class="room-name">Astro Hotel</h5>
                        </div>
                        <div class="room-info-wrap">
                            <span class="room-price">$200 <span class="per-night">Per night</span></span>
                            <p class="paragraph">
                                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                            </p>
                            <a href="#" class="btn rooms-btn">Book now &rarr;</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- <section class="contact">
            <div class="container">
                <h5 class="section-head">
                    <span class="heading">Contact</span>
                    <span class="sub-heading">Get in touch with us</span>
                </h5>
                <div class="contact-content">
                    <div class="traveler-wrap">
                        <img src="./images/traveler.png" alt="" class="">
                    </div>
                    <form action="" class="form contact-form">
                        <div class="input-group-wrap">
                            <div class="input-group">
                                <input type="text" class="input" placeholder="Name" required>
                                <span class="bar"></span>
                            </div>

                            <div class="input-group">
                                <input type="email" class="input" placeholder="E-mail" required>
                                <span class="bar"></span>
                            </div>
                        </div>

                            <div class="input-group">
                                <input type="text" class="input" placeholder="Subject" required>
                                <span class="bar"></span>
                            </div>

                            <div class="input-group">
                                <textarea class="input" cols="30" rows="8" placeholder="E-mail" required></textarea>
                                <span class="bar"></span>
                            </div>

                            <button type="submit" class="btn form-btn btn-blue">Send Message
                                <span class="dots"><i class="fas fa-ellipsis-h"></i></span>
                            </button>

                    </form>
                </div>
            </div>
        </section> -->
    </main>
    </div>
        `,
    
        mounted() {
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
                        
                    });
            }
            
        },
    
        methods: {
            burgerActive : function(){
                
            },

            editUser: function(){
                var jwt = window.localStorage.getItem('jwt');
                if(this.newName !== null && this.newSurname !== null){
                    if(this.newName !== "" && this.newSurname !== ""){
                        if(this.newPassword !== null){
                            if(this.newPassword !== ""){
                            if(this.currentPassword.localeCompare(this.user.password) === 0){
                                if(this.newPassword.localeCompare(this.confPassword) === 0){
                                    if(!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/.test(String(this.newPassword))){
                                        alert("Password is invalid!");
                                        return;
                                    }
                                    axios
                                    .post('/rest/update-user-info', {}, {params: {
                                        Auth: 'Sender ' + jwt,
                                        name: this.newName,
                                        surname: this.newSurname,
                                        password: this.newPassword
                                    }})
                                    .then(res =>{
                                        if(res.data === true){
                                            alert("Successfully updated your information!");
                                        }
                                    });
                                } else {
                                    alert("New password and confirmation password do not match!");
                                }
                            } else {
                                alert("Current password is not correct!");
                            }
                        }else {
                            axios
                                    .post('/rest/update-user-info', {}, {params: {
                                        Auth: 'Sender ' + jwt,
                                        name: this.newName,
                                        surname: this.newSurname,
                                    }})
                                    .then(res =>{
                                        if(res.data === true){
                                            alert("Successfully updated your information!");
                                        }
                                    });
                        }
                        } else {
                            axios
                                    .post('/rest/update-user-info', {}, {params: {
                                        Auth: 'Sender ' + jwt,
                                        name: this.newName,
                                        surname: this.newSurname,
                                    }})
                                    .then(res =>{
                                        if(res.data === true){
                                            alert("Successfully updated your information!");
                                        }
                                    });
                        }
                    }
                }
            },

            openEditingDialog: function(){
                this.editDialogOpen = true;
                this.newName = this.user.name;
                this.newSurname = this.user.surname;
            },

            closeEditingDialog: function(){
                this.newName = "";
                this.newSurname = "";
                this.currentPassword = "";
                this.newPassword = "";
                this.editDialogOpen = false;
                this.confPassword = "";
            },
    
            logout : function(){
                
                var jwt = window.localStorage.getItem('jwt');
                
                if(jwt){
                    window.localStorage.removeItem('jwt');
                    axios
                    .get('/rest/logout', { params: {
                        Auth: 'Sender: ' + jwt
                    }})
                    .then(res => {
                        if(res.data === true){
                            alert("Successfully logged out");
                            window.location.reload();
                        }
                    });
                }
            },
    
            searchUnits : function(){  
                if(this.childrenCount === null){
                    this.childrenCount = 0;
                }
                if(this.adultCount === null){
                    this.adultCount = 0;
                }
    
                if(this.checkIn !== null && this.checkOut !== null){
                    var checkInDate = new Date(this.checkIn);
                    var checkOutDate = new Date(this.checkOut);
                    if(checkInDate.getTime() > checkOutDate.getTime()){
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

                /*
                this.$route.params.destination = this.destination;
                this.$route.params.checkIn = this.checkIn;
                this.$route.params.checkOut = this.checkOut;*/
                window.location.href = '#/search-results?destination=' + this.destination + '&checkIn=' + this.checkIn
                +'&checkOut=' + this.checkOut + '&adultCount=' + this.adultCount + '&childrenCount=' + this.childrenCount;
                

                /*
                axios
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
    */
            }
        },
});

