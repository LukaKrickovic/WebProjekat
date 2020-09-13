Vue.component("units",{ 
    data: function(){
        return {

        }
    },
    
    template: `        <header class="headerUnit">
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
        
        <ul>
            <li v-for="item in units">
                <div class="grid rooms-grid">
                    <div class="grid-item featured-rooms">
                        <div class="image-wrap image-wrapUnit">
                            <img src="./images/room_1.jpg" alt="" class="room-image">
                            <h5 class="room-name">Astro Hotel</h5>
                        </div>
                        <div class="room-info-wrap">
                            <a href="#" class="btn rooms-btnUnit">Edit &#9998;</a><br>
                            <a href="#" class="btn rooms-btnUnit">Delete &#10006;</a><br>
                            <a href="#" class="btn rooms-btnUnit">show more </a><br>
                        </div>
                    </div>
            </li>
        </ul>

    </div>
</section>`
})