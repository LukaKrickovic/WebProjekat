Vue.component("users",{
    data: function(){
        return{
            user: null,
            displayName: "",
            activateTheBurger: false,
            hideFromGuest: false,
            hideFromHost: false,
            username: "",
            name: "",
            surname: "",
            reservationsDialogOpen: false,
            selectedUser: null,
            users: [],
            admins: [],
            hosts: [],
            guests: []
        }
    },

    template: `<body>
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
                        <router-link to="/bookings" class="dropdown-link">Bookings</router-link>
                        <router-link to="/users" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Search for users</router-link>
                        <router-link to="/units" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Units</router-link>
                        <a href="index.html" class="dropdown-link" v-on:click="logout">Logout</a>
                    </div>
                </button>
        </div>
        </nav>
    </div>
</header>

        <section class="admin-users">
            <!-- <div v-if="role === 'ADMINISTRATOR'"> -->
                <h5 class="section-headUnit">
                    <span class="heading">All users</span>
                </h5>
                <div class="container-users">
                    <div class="input-group-users">
                        <label class="input-label-users" for="username">Username:</label>
                        <input type="text" class="input-users" id="username" v-model="username">
                    </div>
                    <div class="input-group-users">
                        <label class="input-label-users" for="name">Name:</label>
                        <input type="text" class="input-users" id="name" v-model="name">
                    </div>
                    <div class="input-group-users">
                        <label class="input-label-users" for="surname">Surname:</label>
                        <input type="text" class="input-users" id="surname" v-model="surname">
                    </div>

                    <button v-on:click="searchUsers" class="btn user-btn-search">Search</button>
                </div>
                

                <ul>
                    <h6 style="font-size: 1.3rem; margin-left: 1rem;">Administrators</h6>
                    <li v-for="item in admins">
                        <div class="grid rooms-grid">
                            <div class="grid-item featured-rooms">
                                <div class="user-wrap">
                                    <h5 class="user-name">{{item.name}} {{item.surname}}</h5>
                                </div>
                                <div class="room-info-wrap">
                                    
                                </div>
                            </div> 
                        </div>
                    </li>

                    <h6 style="font-size: 1.3rem; margin-left: 1rem;">Hosts</h6>

                    <li v-for="item in hosts">
                        <div class="grid rooms-grid">
                            <div class="grid-item featured-rooms">
                                <div class="user-wrap">
                                    <h5 class="user-name">{{item.name}} {{item.surname}}</h5>
                                </div>
                                <div class="room-info-wrap">
                                    <button v-if="item.isBlocked === false" v-on:click="blockUser(item)" class="btn user-btn">Block this user</button>
                                    <button v-if="item.isBlocked === true" v-on:click="unBlockUser(item)" class="btn user-btn">UnBlock this user</button>
                                </div>
                            </div> 
                        </div>
                    </li>

                    <h6 style="font-size: 1.3rem; margin-left: 1rem;">Guests</h6>

                    <li v-for="item in guests">
                        <div class="grid rooms-grid">
                            <div class="grid-item featured-rooms">
                                <div class="user-wrap">
                                    <h5 class="user-name">{{item.name}} {{item.surname}}</h5>
                                </div>
                                <div class="room-info-wrap">
                                    <button v-on:click="makeHost(item)" class="btn user-btn">Make host</button><br>
                                    <button v-if="item.isBlocked === false" v-on:click="blockUser(item)" class="btn user-btn">Block this user</button>
                                    <button v-if="item.isBlocked === true" v-on:click="unBlockUser(item)" class="btn user-btn">UnBlock this user</button>
                                </div>
                            </div> 
                        </div>
                    </li>
                </ul>
            <!-- </div> -->
        </section>
    </div>
</body>`,

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
                        } else if(this.user.role.localeCompare('HOST') === 0){
                            this.hideFromHost = true;
                        }
                    }
                    // alert(this.user);
                });
        }

        axios
            .get('/rest/get-all-admins', { params: 
                {
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                this.admins = res.data;

                if(this.admins !== null){
                    if(this.admins.length > 0){
                        this.empty = false;
                    }
                }

                this.admins = res.data;
                // alert(this.admins.length);
            });

        axios
            .get('/rest/get-all-hosts', { params: 
                {
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                this.hosts = res.data;

                if(this.hosts !== null){
                    if(this.hosts.length > 0){
                        this.empty = false;
                    }
                }

                this.hosts = res.data;
                // alert(this.hosts.length);
            });

        axios
            .get('/rest/get-all-guests', { params: 
                {
                    Auth: 'Sender ' + jwt
                }
            })
            .then(res => {
                this.guests = res.data;

                if(this.guests !== null){
                    if(this.guests.length > 0){
                        this.empty = false;
                    }
                }

                this.guests = res.data;
                // alert(this.guests.length);
            });

            
        
    },

    methods: {
        burgerActive : function(){
            
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

        searchUsers : function(){
            if(this.username === null){
                this.username = "";
            }

            if(this.name === null){
                this.name = "";
            }

            if(this.surname === null){
                this.surname = "";
            }

            window.location.href = '#/search-users?username=' + this.username + '&name=' + this.name
                +'&surname=' + this.surname;
        },

        blockUser: function(item){
            //alert(this.selectedUser.name);
            axios
            .post('/rest/block-user', {}, {params: {
                user: item,
            }})
            .then(res => {
                if(res.data === true){
                    alert("User successfully blocked!");
                }
            });
        },

        unBlockUser: function(item){
            //alert(this.selectedUser.name);
            axios
            .post('/rest/un-block-user', {}, {params: {
                user: item,
            }})
            .then(res => {
                if(res.data === true){
                    alert("User successfully unblocked!");
                }
            });
        },

        makeHost: function(item){
            //alert(this.selectedUser.name);
            axios
            .post('/rest/make-host-user', {}, {params: {
                user: item,
            }})
            .then(res => {
                if(res.data === true){
                    alert("User successfully made host!");
                }
            });
        },

        showReservations: function(item){
            if(window.localStorage.getItem('jwt')){
                this.reservationsDialogOpen = true;
            }
        },
    }
});