Vue.component("bookings",{ 
    data: function(){
        return {
            user: null,
            displayName: "",
            activateTheBurger: false,
            hideFromGuest: false,
            empty: true,
            bookings: [],
            viewComments: false,
            closeComments: false,
            comments: [],
            selectedUnit: null,
            commentsDialogOpen: false,
            newComment: "",
            newGrade: "",
            units: []
        }
    },
    
    template: `        
    <div>
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

<section class="roomsUnit">
    <div class="container">
        <h5 class="section-headUnit">
            <span class="heading">All my bookings</span>
        </h5>
        <br><br>
        <div class="buttons-sort">
            <button v-on:click="sortDesc">Sort by price descending</button><br><br>
            <button v-on:click="sortAsc">Sort by price ascending</button>
            <br><br><br>
        </div>

        <div id="commentsModalDialog" class="modal-booking" v-if="viewComments === true">

                        <!-- Modal content -->
                        <div class="modal-booking-content">
                        <button class="close-x" v-on:click="closecommentsDialog">   
                            <span class="close-booking">&times;</span>
                        </button>
                        <h2 class="modal-headers">Comments for {{selectedUnit.name}}</h2>
                        <ul>
                        
                            <li v-for="comment in comments">
                                <span>"{{comment.text}}", grade: {{comment.grade}}</span>
                                <span v-if="comment.user!==null">, <em>{{comment.user.name}}</em></span>
                                <span v-else>, <em>{{comment.guest.name}}</em></span>
                            </li>
                            
                        </ul>
                        <div class="leaveComment">
                        <input type="text" placeholder="Leave comment" v-model="newComment"></input>
                        <input type="number" placeholder="Grade" v-model="newGrade"></input>
                        <button v-on:click="postComment(item)">Post</button>
                        </div>
                        </div>

                        </div>
        
        <div v-if="empty === false">
            <ul>
                <li v-for="item in bookings">
                    <div class="grid rooms-grid">
                        <div class="grid-item featured-rooms">
                            <div class="image-wrap image-wrapUnit">
                                <img v-bind:src="item.unit.imageSources[0]" alt="" class="room-image">
                                <h5 class="room-name">{{item.unit.name}}</h5>
                            </div>
                            <div class="room-info-wrap">
                                {{item.reservationStatus}}<br><br>
                                <button v-on:click="showComments(item)" class="btn rooms-btnUnit">Comments &#9998;</button><br>
                                <router-link to="/units"
                                    <button v-on:click="cancelReservation(item)" class="btn rooms-btnUnit">Cancel &#10006;</button><br>
                                </router-link>
                                <a href="#" class="btn rooms-btnUnit">Information </a><br>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

    </div>
    </section>
    </div>`,
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

        axios
            .get('/rest/get-bookings-for-user', { params: 
                {
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                this.bookings = res.data;

                if(this.bookings !== null){
                    if(this.bookings.length > 0){
                        this.empty = false;
                    }
                }

                this.bookings = res.data;
                alert(this.bookings.length);
            });
        
    },
    methods: {
        burgerActive : function(){
                
        },

        sortDesc: function(){
            this.searchResults.sort(function (result1, result2) {

                // Sort by price
                // If the first item has a higher number, move it down
                // If the first item has a lower number, move it up
                if (parseFloat(result1.pricePerNight) > parseFloat(result2.pricePerNight)) return -1;
                if (parseFloat(result1.pricePerNight) < parseFloat(result2.pricePerNight)) return 1;
            
            });
        },

        sortAsc: function(){
            this.searchResults.sort(function (result1, result2) {

                // Sort by price
                // If the first item has a higher number, move it down
                // If the first item has a lower number, move it up
                if (parseFloat(result1.pricePerNight) < parseFloat(result2.pricePerNight)) return -1;
                if (parseFloat(result1.pricePerNight) > parseFloat(result2.pricePerNight)) return 1;
            
            });
        },

        enableComments : function(item){
            this.viewComments = true;
            this.selectedUnit = item.unit;
            alert(this.comments.length);
        },

        cancelReservation: function(item){
            axios
                .delete('/rest/cancel-reservation', { params:
                    {
                        reservation: item,
                        user: this.user
                    }
                })
                .then(res => {
                    alert(res);
                    if(res.data === true){
                        alert("Reservation cancelled successfully!");
                    } else {
                        alert("Failed to cancel reservation!");
                    }
                });
        },

        closecommentsDialog: function(){
            this.viewComments = false;
        },

        postComment : function(item){
            axios
            .post('/rest/post-comment', {}, {params: {
                user: this.user,
                comment: this.newComment,
                grade: this.newGrade,
                unit: this.unit
            }})
            .then(res => {
                if(res.data === true){
                    alert("Comment successfully posted!");
                    this.closecommentsDialog();
                }
            });
        },

        showComments : function(item){
            if(this.user.role.localeCompare('GUEST') === 0){
            axios
            .get('/rest/get-comments-for-apartment-guest', { params: {
                unit: item.unit,
                user: this.user
            }})
            .then(res => {
                if(res.data !== null){
                    this.comments = res.data;
                    this.commentsDialogOpen = true;
                }
            });
            }
            else{
                axios
                .get('/rest/get-comments-for-apartment-host', { params: {
                    unit: item.unit,
                    user: this.user
                }})
                .then(res => {
                    if(res.data !== null){
                        this.comments = res.data;
                        alert(res.data.length);
                        alert("Ucitalo je commentse!!");
                        alert(this.comments.length);
                        this.commentsDialogOpen = true;
                    }
                });
    
                } 
            this.viewComments = true;
            alert(item.unit);
            this.selectedUnit = item.unit;
            alert("You just selected: " + this.selectedUnit);
            alert(this.comments.length);
        },

        logout : function(){
            alert("LOGOUT");
            var jwt = window.localStorage.getItem('jwt');
            alert(jwt);
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
    }
});