Vue.component("units",{ 
    data: function(){
        return {
            user: null,
            displayName: "",
            activateTheBurger: false,
            hideFromGuest: false,
            empty: true,
            units: []
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
                                <button class="btn rooms-btnUnit">Edit &#9998;</button><br>
                                <router-link to="/units"
                                    <button v-on:click="deleteUnit(item)" class="btn rooms-btnUnit">Delete &#10006;</button><br>
                                </router-link>
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