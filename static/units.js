Vue.component("units",{ 
    data: function(){
        return {
            user: null,
            displayName: "",
            activateTheBurger: false,
            hideFromGuest: false,
            empty: true,
            selectedUnit: null,
            comments: [],
            viewComments: false,
            commentsDialogOpen: false,
            newComment: "",
            newGrade: 0,
            units: [],
            editDialogOpen: false,
            selectedUnitForEditing: null,
            roomType: "",
	        numOfRooms: "",
	        numOfGuests: "",
            location: "",
            zipCode: "",
	        host: "",
	        pricePerNight: "",
	        checkinTime: "",
	        checkoutTime: "",
            status: "",
            street: "",
            buildingNumber: "",
            city: "",
            newAmenities: [],
	        amenities: [],
	        imageSources: [],
	        name: ""
        }
    },
    
    template: `        
    <div>
    <header class="headerUnit">
    <div class="container" id="container">
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
                        <a href="#" class="dropdown-link" v-on:click="logout">Logout</a>
                    </div>
                </button>
        </div>
        </nav>
    </div>
</header>

<section class="roomsUnit">
    <div class="container">
        <h5 class="section-headUnit">
            <span class="heading">All my units</span>
            <a href="#" class="btn btn-gradient btnUnit">&#10010; Add unit </a>
        </h5>
        

        <div id="commentsModalDialog" class="modal-booking" v-if="viewComments === true">

        <!-- Modal content -->
        <div class="modal-booking-content">
        <button class="close-x" v-on:click="closecommentsDialog">   
            <span class="close-booking">&times;</span>
        </button>
        <h2 class="modal-headers">Comments for {{selectedUnit.name}}</h2>
        <br><br>
        <ul>
        
            <li v-for="comment in comments">
                <span>"{{comment.text}}", grade: {{comment.grade}}&#9733;</span>
                <span v-if="comment.user!==null">, <em>{{comment.user.name}}</em></span>
                <span v-else>, <em>{{comment.guest.name}}</em></span>
                <button v-if="comment.isApproved === false" v-on:click="approveComment(comment)">Approve</button>
            </li>
            <br><br>
            
        </ul>
        <div class="leaveComment">
        <input type="text" placeholder="Leave comment" v-model="newComment"></input>
        <input type="number" placeholder="Grade" v-model="newGrade"></input>
        <button v-on:click="postComment">Post</button>
        </div>
        </div>
        </div>

        <div id="editModalDialog" class="modal-booking" v-if="editDialogOpen === true">

            <!-- Modal content -->
            <div class="modal-booking-content">
            <button class="close-x" v-on:click="closeEditingDialog">   
                <span class="close-booking">&times;</span>
            </button>
            <h2 class="modal-headers">Enter unit information here!</h2>
            <h4 class="modal-headers">Editing -> {{selectedUnitForEditing.name}}</h4>
            <br><br>
            <label>Name:</label>
            <input type="text" name="name" placeholder="Name" v-model="name">
            <label>Room type:</label>
            <input type="text" name="roomType" placeholder="RoomType" v-model="roomType">
            <label>Number of rooms:</label>
            <input type="number" name="numOfRooms" placeholder="NumOfRooms" v-model="numOfRooms">
            <label>Number of guests:</label>
            <input type="number" name="numOfGuests" placeholder="NumOfGuests" v-model="numOfGuests">
            <label>Price per night:</label>
            <input type="number" name="pricePerNight" placeholder="PricePerNight" v-model="pricePerNight">
            <label>Street:</label>
            <input type="text" name="street" placeholder="Street" v-model="street">
            <label>Building number:</label>
            <input type="text" name="buildingNumber" placeholder="Building number" v-model="buildingNumber">
            <label>City:</label>
            <input type="text" name="city" placeholder="City" v-model="city">
            <label>ZipCode:</label>
            <input type="text" name="zipCode" placeholder="ZipCode" v-model="zipCode">
            <label>Amenities:</label>
            <label>Please separate them with commas! (ie. air conditioning, free parking)</label>
            <input type="text" name="newAmenities" placeholder="Amenities" v-model="newAmenities">
            <label>Already added amenities:</label>
            <div>
            <ul>
                <li clas="amenity-editing-list" v-for="am in selectedUnitForEditing.amenities">
                    <span>{{am.description}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><button v-on:click="removeAmenity(am)">x</button>
                </li>
            </ul>
            </div>
            <br><br>
            <button v-on:click="editUnit">Confirm</button>
            </div>


        </div>

        <div v-if="empty === false">
            <ul>
                <li v-for="item in units">
                    <div class="grid rooms-grid">
                        <div class="grid-item featured-rooms">
                            <div class="image-wrap image-wrapUnit">
                                <img src="./images/room_1.jpg" alt="" class="room-image">
                                <h5 class="room-name">{{item.name}}</h5>
                            </div>
                            <div class="room-info-wrap">
                                <button v-on:click="openEditingDialog(item)" class="btn rooms-btnUnit">Edit &#9998;</button><br>
                                <router-link to="/units"
                                    <button v-on:click="deleteUnit(item)" class="btn rooms-btnUnit">Delete &#10006;</button><br>
                                </router-link>
                                <button v-on:click="showComments(item)">Show comments</button>
                                <a href="#" class="btn rooms-btnUnit">show more </a><br>
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
            .get('/rest/get-units-for-host', { params: 
                {
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                this.units = res.data;

                if(this.units !== null){
                    if(this.units.length > 0){
                        this.empty = false;
                    }
                }

                this.units = res.data;
                alert(this.units.length);
            });
        
    },
    methods: {
        burgerActive : function(){
                
        },


        closecommentsDialog: function(){
            this.viewComments = false;
        },

        removeAmenity: function(am){
            axios
            .delete('/rest/remove-amenity', {params: {
                amenity: am,
                user: this.user,
                unit: this.selectedUnitForEditing
            }})
            .then(res =>{
                if(res.data === true){
                    this.selectedUnitForEditing.amenities.removeItem(am);
                } else {
                    alert("Failed to remove amenity");
                }
            });
        },

        postComment: function(){
            alert(this.selectedUnit.name);
            axios
            .post('/rest/post-comment', {}, {params: {
                user: this.user,
                comment: this.newComment,
                grade: this.newGrade,
                unit: this.selectedUnit
            }})
            .then(res => {
                if(res.data === true){
                    alert("Comment successfully posted!");
                    this.closecommentsDialog();
                }
            });
        },


        editUnit: function(){
            axios
            .post('/rest/edit-unit', {}, { params: {
                roomType: this.roomType,
                numOfRooms: this.numOfRooms,
                numOfGuests: this.numOfGuests,
                pricePerNight: this.selectedUnitForEditing.pricePerNight,
                status: this.status,
                name: this.name,
                street: this.street,
                buildingNumber: this.buildingNumber,
                city: this.city,
                zipCode: this.zipCode,
                newAmenities: this.newAmenities,
                user: this.user,
                unit: this.selectedUnitForEditing
            } })
            .then(res => {
                if(res.data === true){
                    alert("Editing for " + this.selectedUnitForEditing.name + " was successful!");
                    this.closeEditingDialog();
                } else {
                    alert("Failed to edit " + this.selectedUnitForEditing.name + "!");
                }
            });
        },

        showComments: function(item){
            this.newComment = "";
            this.newGrade = 0;
            alert('Tu sam');
                axios
                .get('/rest/get-comments-for-host', { params: {
                    unit: item,
                    user: this.user
                }})
                .then(res => {
                    if(res.data !== null){
                        this.comments = res.data;
                        this.commentsDialogOpen = true;
                        this.viewComments = true;
                        this.selectedUnit = item;
                    }
                });
    
                
            
        },

        approveComment: function(comment){
            axios
            .post('/rest/approve-comment', {}, {params: {
                user: this.user,
                aptComment: comment
            }})
            .then(res=>{
                if(res.data === true){
                    alert("Successfully approved comment!");
                } else {
                    alert("Failed to approve comment!");
                }
            });
        },

        openEditingDialog: function(item){
            if(window.localStorage.getItem('jwt')){
                this.street = item.location.address.street,
                this.buildingNumber = item.location.address.number,
                this.city = item.location.address.city,
                this.country = item.location.address.country,
                this.zipCode = item.location.address.zipCode,
                this.editDialogOpen = true;
                this.selectedUnitForEditing = item;
                this.roomType = item.roomType;
                this.numOfRooms = item.numOfRooms;
                this.numOfGuests = item.numOfGuests;
                this.pricePerNight = item.pricePerNight;
                this.status = item.status;
                this.amenities = item.amenities;
                this.imageSources = item.imageSources;
                this.name = item.name;
            }
        },

        closeEditingDialog: function(){
            this.editDialogOpen = false;
        },

        deleteUnit: function(item){
            axios
                .delete('/rest/delete-unit', { params:
                    {
                        unit: item,
                        user: this.user
                    }
                })
                .then(res => {
                    alert(res);
                    if(res.data === true){
                        alert("Unit deleted successfully!");
                    } else {
                        alert("Failed to delete unit!");
                    }
                });
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