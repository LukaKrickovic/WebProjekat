Vue.component("units",{ 
    data: function(){
        return {
            user: null,
            displayName: "",
            newReservationsDialogOpen: false,
            reservationCountMap: new Map(),
            createdUnit: null,
            uploadedFileNames: [],
            activateTheBurger: false,
            hideFromGuest: false,
            empty: true,
            selectedUnit: null,
            confString: "conf",
            decString: "dec",
            comments: [],
            viewComments: false,
            commentsDialogOpen: false,
            newComment: "",
            selectedFile: null,
            newGrade: 0,
            tempId:{},
            newImages:[],
            newUnitCountry: "",
            units: [],
            images:[],
            imageCount: 0,
            editDialogOpen: false,
            selectedUnitForEditing: null,
            tempApt: {},
            tempLocation:{},
            addImagesModalDialogOpen: false,
            tempAddress:{},
            roomType: "",
            reservationsEmpty: true,
            newUnitCheckInTime: null,
            newUnitCheckOutTime: null,
            newReservationsForUnit: [],
            newReservationsForUnitEmpty: false,
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
            newUnitName: "",
            newUnitRoomType: "",
            newUnitNumOfRooms: 0,
            newUnitNumOfGuests: 0,
            newUnitPricePerNight: 0.0,
            newUnitStreet: "",
            newUnitBuildingNumber: "",
            newUnitCity: "",
            newUnitZipCode: "",
            newUnitAmenities: [],
            newUnitImageSources: [],
            newUnitDialogOpen: false,
            tempAmenities: [],
            tempAmenity:{},
            splitAmenities: [],
            newUnitLatitude: "",
            newUnitLongitude: "",
	        name: ""
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
                            <router-link to="/users" class="dropdown-link" v-bind:class="{ hidden: hideFromGuest }">Search for users</router-link>
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
            <span class="heading">All my units</span>
            <button v-on:click="openNewUnitDialog" class="btn btn-gradient btnUnit">&#10010; Add unit </button>
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

        <div id="newReservationsModalDialog" class="modal-booking" v-if="newReservationsDialogOpen === true">

            <!-- Modal content -->
            <div class="modal-booking-content">
            <button class="close-x" v-on:click="closeNewReservationsDialog">   
                <span class="close-booking">&times;</span>
            </button>
            <h2 class="modal-headers">See your new created reservations!</h2>
            <h4 class="modal-headers">Confirm or decline them here!</h4>
            <br><br>
            <div v-if="newReservationsForUnitEmpty === false">
                <ul>
                    <li v-for="res in newReservationsForUnit">
                        <div>
                            <span>Check in date: {{res.startDate}}, check out date: {{res.endDate}} &nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <button v-on:click="alterResStatus(res, confString)">Confirm</button>
                            <button v-on:click="alterResStatus(res, decString)">Decline</button>
                        </div>
                    </li>
                </ul>
            </div>
            <div v-else>
                <span>You have no new reservations!</span>
            </div>
            </div>


        </div>

        <div id="newUnitModalDialog" class="modal-booking" v-if="newUnitDialogOpen === true">

            <!-- Modal content -->
            <div class="modal-booking-content">
            <button class="close-x" v-on:click="closeNewUnitDialog">   
                <span class="close-booking">&times;</span>
            </button>
            <h2 class="modal-headers">Enter unit information here!</h2>
            <br><br>
            <label>Name:</label>
            <input type="text" name="name" placeholder="Name" v-model="newUnitName">
            <label>Room type:</label>
            <select name="roomType" placeholder="RoomType" v-model="newUnitRoomType">
                <option value="APARTMENT">Apartment</option>
                <option value="ROOM">Room</option>
            </select>
            <label>Number of rooms:</label>
            <input type="number" name="numOfRooms" placeholder="NumOfRooms" v-model="newUnitNumOfRooms">
            <label>Number of guests:</label>
            <input type="number" name="numOfGuests" placeholder="NumOfGuests" v-model="newUnitNumOfGuests">
            <label>Price per night:</label>
            <input type="number" name="pricePerNight" placeholder="PricePerNight" v-model="newUnitPricePerNight">
            <label>Street:</label>
            <input type="text" name="street" placeholder="Street" v-model="newUnitStreet">
            <label>Building number:</label>
            <input type="text" name="buildingNumber" placeholder="Building number" v-model="newUnitBuildingNumber">
            <label>City:</label>
            <input type="text" name="city" placeholder="City" v-model="newUnitCity">
            <label>ZipCode:</label>
            <input type="text" name="zipCode" placeholder="ZipCode" v-model="newUnitZipCode">
            <label>Country:</label>   
        
            <select id="country" name="country" class="form-control" v-model="newUnitCountry">
                <option value="Afghanistan">Afghanistan</option>
                <option value="Åland Islands">Åland Islands</option>
                <option value="Albania">Albania</option>
                <option value="Algeria">Algeria</option>
                <option value="American Samoa">American Samoa</option>
                <option value="Andorra">Andorra</option>
                <option value="Angola">Angola</option>
                <option value="Anguilla">Anguilla</option>
                <option value="Antarctica">Antarctica</option>
                <option value="Antigua and Barbuda">Antigua and Barbuda</option>
                <option value="Argentina">Argentina</option>
                <option value="Armenia">Armenia</option>
                <option value="Aruba">Aruba</option>
                <option value="Australia">Australia</option>
                <option value="Austria">Austria</option>
                <option value="Azerbaijan">Azerbaijan</option>
                <option value="Bahamas">Bahamas</option>
                <option value="Bahrain">Bahrain</option>
                <option value="Bangladesh">Bangladesh</option>
                <option value="Barbados">Barbados</option>
                <option value="Belarus">Belarus</option>
                <option value="Belgium">Belgium</option>
                <option value="Belize">Belize</option>
                <option value="Benin">Benin</option>
                <option value="Bermuda">Bermuda</option>
                <option value="Bhutan">Bhutan</option>
                <option value="Bolivia">Bolivia</option>
                <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
                <option value="Botswana">Botswana</option>
                <option value="Bouvet Island">Bouvet Island</option>
                <option value="Brazil">Brazil</option>
                <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
                <option value="Brunei Darussalam">Brunei Darussalam</option>
                <option value="Bulgaria">Bulgaria</option>
                <option value="Burkina Faso">Burkina Faso</option>
                <option value="Burundi">Burundi</option>
                <option value="Cambodia">Cambodia</option>
                <option value="Cameroon">Cameroon</option>
                <option value="Canada">Canada</option>
                <option value="Cape Verde">Cape Verde</option>
                <option value="Cayman Islands">Cayman Islands</option>
                <option value="Central African Republic">Central African Republic</option>
                <option value="Chad">Chad</option>
                <option value="Chile">Chile</option>
                <option value="China">China</option>
                <option value="Christmas Island">Christmas Island</option>
                <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
                <option value="Colombia">Colombia</option>
                <option value="Comoros">Comoros</option>
                <option value="Congo">Congo</option>
                <option value="Congo, The Democratic Republic of The">Congo, The Democratic Republic of The</option>
                <option value="Cook Islands">Cook Islands</option>
                <option value="Costa Rica">Costa Rica</option>
                <option value="Cote D'ivoire">Cote D'ivoire</option>
                <option value="Croatia">Croatia</option>
                <option value="Cuba">Cuba</option>
                <option value="Cyprus">Cyprus</option>
                <option value="Czech Republic">Czech Republic</option>
                <option value="Denmark">Denmark</option>
                <option value="Djibouti">Djibouti</option>
                <option value="Dominica">Dominica</option>
                <option value="Dominican Republic">Dominican Republic</option>
                <option value="Ecuador">Ecuador</option>
                <option value="Egypt">Egypt</option>
                <option value="El Salvador">El Salvador</option>
                <option value="Equatorial Guinea">Equatorial Guinea</option>
                <option value="Eritrea">Eritrea</option>
                <option value="Estonia">Estonia</option>
                <option value="Ethiopia">Ethiopia</option>
                <option value="Falkland Islands (Malvinas)">Falkland Islands (Malvinas)</option>
                <option value="Faroe Islands">Faroe Islands</option>
                <option value="Fiji">Fiji</option>
                <option value="Finland">Finland</option>
                <option value="France">France</option>
                <option value="French Guiana">French Guiana</option>
                <option value="French Polynesia">French Polynesia</option>
                <option value="French Southern Territories">French Southern Territories</option>
                <option value="Gabon">Gabon</option>
                <option value="Gambia">Gambia</option>
                <option value="Georgia">Georgia</option>
                <option value="Germany">Germany</option>
                <option value="Ghana">Ghana</option>
                <option value="Gibraltar">Gibraltar</option>
                <option value="Greece">Greece</option>
                <option value="Greenland">Greenland</option>
                <option value="Grenada">Grenada</option>
                <option value="Guadeloupe">Guadeloupe</option>
                <option value="Guam">Guam</option>
                <option value="Guatemala">Guatemala</option>
                <option value="Guernsey">Guernsey</option>
                <option value="Guinea">Guinea</option>
                <option value="Guinea-bissau">Guinea-bissau</option>
                <option value="Guyana">Guyana</option>
                <option value="Haiti">Haiti</option>
                <option value="Heard Island and Mcdonald Islands">Heard Island and Mcdonald Islands</option>
                <option value="Holy See (Vatican City State)">Holy See (Vatican City State)</option>
                <option value="Honduras">Honduras</option>
                <option value="Hong Kong">Hong Kong</option>
                <option value="Hungary">Hungary</option>
                <option value="Iceland">Iceland</option>
                <option value="India">India</option>
                <option value="Indonesia">Indonesia</option>
                <option value="Iran, Islamic Republic of">Iran, Islamic Republic of</option>
                <option value="Iraq">Iraq</option>
                <option value="Ireland">Ireland</option>
                <option value="Isle of Man">Isle of Man</option>
                <option value="Israel">Israel</option>
                <option value="Italy">Italy</option>
                <option value="Jamaica">Jamaica</option>
                <option value="Japan">Japan</option>
                <option value="Jersey">Jersey</option>
                <option value="Jordan">Jordan</option>
                <option value="Kazakhstan">Kazakhstan</option>
                <option value="Kenya">Kenya</option>
                <option value="Kiribati">Kiribati</option>
                <option value="Korea, Democratic People's Republic of">Korea, Democratic People's Republic of</option>
                <option value="Korea, Republic of">Korea, Republic of</option>
                <option value="Kuwait">Kuwait</option>
                <option value="Kyrgyzstan">Kyrgyzstan</option>
                <option value="Lao People's Democratic Republic">Lao People's Democratic Republic</option>
                <option value="Latvia">Latvia</option>
                <option value="Lebanon">Lebanon</option>
                <option value="Lesotho">Lesotho</option>
                <option value="Liberia">Liberia</option>
                <option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
                <option value="Liechtenstein">Liechtenstein</option>
                <option value="Lithuania">Lithuania</option>
                <option value="Luxembourg">Luxembourg</option>
                <option value="Macao">Macao</option>
                <option value="Macedonia, The Former Yugoslav Republic of">Macedonia, The Former Yugoslav Republic of</option>
                <option value="Madagascar">Madagascar</option>
                <option value="Malawi">Malawi</option>
                <option value="Malaysia">Malaysia</option>
                <option value="Maldives">Maldives</option>
                <option value="Mali">Mali</option>
                <option value="Malta">Malta</option>
                <option value="Marshall Islands">Marshall Islands</option>
                <option value="Martinique">Martinique</option>
                <option value="Mauritania">Mauritania</option>
                <option value="Mauritius">Mauritius</option>
                <option value="Mayotte">Mayotte</option>
                <option value="Mexico">Mexico</option>
                <option value="Micronesia, Federated States of">Micronesia, Federated States of</option>
                <option value="Moldova, Republic of">Moldova, Republic of</option>
                <option value="Monaco">Monaco</option>
                <option value="Mongolia">Mongolia</option>
                <option value="Montenegro">Montenegro</option>
                <option value="Montserrat">Montserrat</option>
                <option value="Morocco">Morocco</option>
                <option value="Mozambique">Mozambique</option>
                <option value="Myanmar">Myanmar</option>
                <option value="Namibia">Namibia</option>
                <option value="Nauru">Nauru</option>
                <option value="Nepal">Nepal</option>
                <option value="Netherlands">Netherlands</option>
                <option value="Netherlands Antilles">Netherlands Antilles</option>
                <option value="New Caledonia">New Caledonia</option>
                <option value="New Zealand">New Zealand</option>
                <option value="Nicaragua">Nicaragua</option>
                <option value="Niger">Niger</option>
                <option value="Nigeria">Nigeria</option>
                <option value="Niue">Niue</option>
                <option value="Norfolk Island">Norfolk Island</option>
                <option value="Northern Mariana Islands">Northern Mariana Islands</option>
                <option value="Norway">Norway</option>
                <option value="Oman">Oman</option>
                <option value="Pakistan">Pakistan</option>
                <option value="Palau">Palau</option>
                <option value="Palestinian Territory, Occupied">Palestinian Territory, Occupied</option>
                <option value="Panama">Panama</option>
                <option value="Papua New Guinea">Papua New Guinea</option>
                <option value="Paraguay">Paraguay</option>
                <option value="Peru">Peru</option>
                <option value="Philippines">Philippines</option>
                <option value="Pitcairn">Pitcairn</option>
                <option value="Poland">Poland</option>
                <option value="Portugal">Portugal</option>
                <option value="Puerto Rico">Puerto Rico</option>
                <option value="Qatar">Qatar</option>
                <option value="Reunion">Reunion</option>
                <option value="Romania">Romania</option>
                <option value="Russian Federation">Russian Federation</option>
                <option value="Rwanda">Rwanda</option>
                <option value="Saint Helena">Saint Helena</option>
                <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
                <option value="Saint Lucia">Saint Lucia</option>
                <option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
                <option value="Saint Vincent and The Grenadines">Saint Vincent and The Grenadines</option>
                <option value="Samoa">Samoa</option>
                <option value="San Marino">San Marino</option>
                <option value="Sao Tome and Principe">Sao Tome and Principe</option>
                <option value="Saudi Arabia">Saudi Arabia</option>
                <option value="Senegal">Senegal</option>
                <option value="Serbia">Serbia</option>
                <option value="Seychelles">Seychelles</option>
                <option value="Sierra Leone">Sierra Leone</option>
                <option value="Singapore">Singapore</option>
                <option value="Slovakia">Slovakia</option>
                <option value="Slovenia">Slovenia</option>
                <option value="Solomon Islands">Solomon Islands</option>
                <option value="Somalia">Somalia</option>
                <option value="South Africa">South Africa</option>
                <option value="South Georgia and The South Sandwich Islands">South Georgia and The South Sandwich Islands</option>
                <option value="Spain">Spain</option>
                <option value="Sri Lanka">Sri Lanka</option>
                <option value="Sudan">Sudan</option>
                <option value="Suriname">Suriname</option>
                <option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
                <option value="Swaziland">Swaziland</option>
                <option value="Sweden">Sweden</option>
                <option value="Switzerland">Switzerland</option>
                <option value="Syrian Arab Republic">Syrian Arab Republic</option>
                <option value="Taiwan, Province of China">Taiwan, Province of China</option>
                <option value="Tajikistan">Tajikistan</option>
                <option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
                <option value="Thailand">Thailand</option>
                <option value="Timor-leste">Timor-leste</option>
                <option value="Togo">Togo</option>
                <option value="Tokelau">Tokelau</option>
                <option value="Tonga">Tonga</option>
                <option value="Trinidad and Tobago">Trinidad and Tobago</option>
                <option value="Tunisia">Tunisia</option>
                <option value="Turkey">Turkey</option>
                <option value="Turkmenistan">Turkmenistan</option>
                <option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
                <option value="Tuvalu">Tuvalu</option>
                <option value="Uganda">Uganda</option>
                <option value="Ukraine">Ukraine</option>
                <option value="United Arab Emirates">United Arab Emirates</option>
                <option value="United Kingdom">United Kingdom</option>
                <option value="United States">United States</option>
                <option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
                <option value="Uruguay">Uruguay</option>
                <option value="Uzbekistan">Uzbekistan</option>
                <option value="Vanuatu">Vanuatu</option>
                <option value="Venezuela">Venezuela</option>
                <option value="Viet Nam">Viet Nam</option>
                <option value="Virgin Islands, British">Virgin Islands, British</option>
                <option value="Virgin Islands, U.S.">Virgin Islands, U.S.</option>
                <option value="Wallis and Futuna">Wallis and Futuna</option>
                <option value="Western Sahara">Western Sahara</option>
                <option value="Yemen">Yemen</option>
                <option value="Zambia">Zambia</option>
                <option value="Zimbabwe">Zimbabwe</option>
            </select>
            <label>Latitude:</label>
            <input type="text" name="latitude" placeholder="Latitude" v-model="newUnitLatitude">
            <label>Longitude:</label>
            <input type="text" name="longitude" placeholder="Longitude" v-model="newUnitLongitude">
            <label>Check in time:</label>
            <input type="time" placeholder="Check in time" v-model="newUnitCheckInTime"></input>
            <label>Check out time:</label>
            <input type="time" placeholder="Check out time" v-model="newUnitCheckOutTime"></input>
            <label>Amenities:</label>
            <label>Please separate them with commas! (ie. air conditioning, free parking)</label>
            <input type="text" name="newAmenities" placeholder="Amenities" v-model="newUnitAmenities">
            
            <br><br>
            <button v-on:click="createUnit">Confirm</button>
            </div>


        </div>
        <div id="addImagesModalDialog" class="modal-booking" v-if="addImagesModalDialogOpen === true">

            <!-- Modal content -->
            <div class="modal-booking-content">
            <button class="close-x" v-on:click="closeImagesModalDialog">   
                <span class="close-booking">&times;</span>
            </button>
            <h2 class="modal-headers">Upload images for your unit!</h2>
            <br><br>
            
            <label>Select images here!</label>
            <input type="file" accept="image/*" v-on:change="onFileUpload"></input>
            <br><br>
            <button v-on:click="onUpload">Confirm</button>
            </div>


        </div>

        <div v-if="empty === false">
            <ul>
                <li v-for="item in units">
                    <div class="grid rooms-grid">
                        <div class="grid-item featured-rooms">
                            <div class="image-wrap image-wrapUnit">
                                <img v-bind:src="item.imageSources[0]" alt="" class="room-image">
                                <h5 class="room-name">{{item.name}}</h5>
                            </div>
                            <div class="room-info-wrap">
                                <button v-on:click="openEditingDialog(item)" class="btn rooms-btnUnit">Edit &#9998;</button><br>
                                <router-link to="/units"
                                    <button v-on:click="deleteUnit(item)" class="btn rooms-btnUnit">Delete &#10006;</button><br>
                                </router-link>
                                <button v-on:click="showComments(item)">Show comments</button>
                                <button v-on:click="showNewReservations(item)">
                                <span>New reservations</span></button>
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
                
            });

        axios
        .get('/rest/reservation-map',{ params: {
            Auth: "Sender " + jwt
        }})
        .then(res => {
            if(res.data !== null){
                if(res.data.length > 0){
                    this.reservationCountMap = res.data;
                    this.reservationsEmpty = false;
                    console.log(res.data);
                } else {
                    this.reservationsEmpty = true;
                }
            }
        });
        
    },
    methods: {
        burgerActive : function(){
                
        },

        alterResStatus: function(res, stat){
            var jwt = window.localStorage.getItem('jwt');
            
            axios
            .post('/rest/alter-res-status', {},{params: {
                reservation: res,
                status: stat,
                Auth: 'Sender ' + jwt
            }})
            .then(res => {
                if(res.data !== null){
                    if(res.data === true){
                        console.log("Success");
                        this.closeNewReservationsDialog();
                    }
                }
            })
        },

        showNewReservations: function(item){
            var jwt = window.localStorage.getItem('jwt');
            this.newReservationsDialogOpen = true;
            axios
            .get('/rest/new-reservations', {params: {
                unit: item,
                Auth: 'Sender ' + jwt
            }})
            .then(res => {
                if(res.data !== null){
                    if(res.data.length > 0){
                        this.newReservationsForUnit = res.data;
                        this.newReservationsForUnitEmpty = false;
                    } else {
                        this.newReservationsForUnitEmpty = true;
                    }
                }
            });
        },

        closeNewReservationsDialog: function(){
            this.newReservationsDialogOpen = false;
        },

        openNewUnitDialog: function(){
            this.newUnitDialogOpen = true;
        },

        onFileUpload(event){
            this.selectedFile = event.target.files[0];
        },

        onUpload: function(){
            const fd = new FormData();
            fd.append('image', this.selectedFile, this.selectedFile.name);
            fd.append('unit', JSON.stringify(this.createdUnit));
            fd.append('host', JSON.stringify(this.user));
            axios
            .post('/rest/upload-image', fd)
            .then(res => {
                if(res.data !== null){
                    alert("Upoaded image!");
                    this.units = res.data;
                    this.closeImagesModalDialog();
                    this.window.location.reload();
                }
            });
        },
        /*onFileUpload(event){
            this.newUnitImageSources = event.target.files;
            this.uploadedFileNames = event.target.files;
        },*/

        /*onFileUpload(e){
            const file = e.target.files[0];
            alert("IMGADDED");
            this.createBase64(file, e);
            this.imageCount++;
            this.images.push(URL.createObjectURL(file));
        },

        createBase64(file, e){
            alert("CreatingB64");
            //const reader = new FileReader();
            alert("In reader");
            let img = e.target.result;
            img.replace("data:image\/(png|jpg|jpeg);base64","");
            alert(img);
            this.newImages.push(img);
            
        },*//*
        createBase64(file){
            alert("CreatingB64");
            const reader = new FileReader();
            reader.onload=(e)=>{
                alert("In reader");
                let img = e.target.result;
                img.replace("data:image\/(png|jpg|jpeg);base64","");
                alert(img);
                this.newImages.push(img);
            }
        },*/

        closeNewUnitDialog: function(){
            this.newUnitDialogOpen = false;
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
            if(this.validateInput()){
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
                country: this.country,
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
        }
        },

        closeImagesModalDialog: function(){
            this.addImagesModalDialogOpen = false;
        },

        validateInput(){
            if(this.validateRoomType() && this.validateNumOfGuests() && this.validateNumOfRooms() && this.validatePricePerNight() && this.validateName() && this.validateStreet() && this.validateBuildingNumber() && this.validateCity() && this.validateCountry() && this.validateZipCode()){
                return true;
            } else {
                return false;
            }
        },

        validateRoomType(){
            if(this.newUnitRoomType === ""){
                return false;
            } else if(this.newUnitRoomType === null){
                return false;
            }

            return true;
        },
        
        validateNumOfGuests(){
            if(this.newUnitNumOfGuests === 0){
                return false;
            } else if(this.newUnitNumOfGuests === null){
                return false;
            }

            return true;
        },
        
        validateNumOfRooms(){
            if(this.newUnitNumOfRooms === 0){
                return false;
            } else if(this.newUnitNumOfRooms === null){
                return false;
            }

            return true;
        },
        
        validatePricePerNight(){
            if(this.newUnitPricePerNight === 0.0){
                return false;
            } else if(this.newUnitPricePerNight === null){
                return false;
            }

            return true;
        },
        
        validateName(){
            if(this.newUnitName === ""){
                return false;
            } else if(this.newUnitName === null){
                return false;
            }

            return true;
        },
        
        validateBuildingNumber(){
            if(this.newUnitBuildingNumber === ""){
                return false;
            } else if(this.newUnitBuildingNumber === null){
                return false;
            }

            return true;
        },
        
        validateStreet(){
            if(this.newUnitStreet === ""){
                return false;
            } else if(this.newUnitStreet === null){
                return false;
            }

            return true;
        },
        
        validateZipCode(){
            if(this.newUnitZipCode === ""){
                return false;
            } else if(this.newUnitZipCode === null){
                return false;
            }

            return true;
        },
        
        validateCountry(){
            if(this.newUnitCountry === ""){
                return false;
            } else if(this.newUnitCountry === null){
                return false;
            }

            return true;
        },
        
        validateCity(){
            if(this.newUnitCity === ""){
                return false;
            } else if(this.newUnitCity === null){
                return false;
            }

            return true;
        },

        createUnit: function(){
            /*
            const fd = new FormData();
            fd.append("images", this.newUnitImageSources)
            fd.append("roomType", this.newUnitRoomType);
            fd.append("numOfRooms", this.newUnitNumOfRooms);
            fd.append("numOfGuests", this.newUnitNumOfGuests);
            fd.append("pricePerNight", this.newUnitPricePerNight);
            fd.append("status", "ACTIVE");
            fd.append("name", this.newUnitName);
            fd.append("street", this.newUnitStreet);
            fd.append("buildingNumber", this.newUnitBuildingNumber);
            fd.append("city", this.newUnitCity);
            fd.append("zipCode", this.newUnitZipCode);
            fd.append("newAmenities", this.newUnitAmenities);
            fd.append("user", this.user);
            */
            if(this.validateInput()){
            axios
            .post('/rest/create-unit', {}, {params:{
                roomType: this.newUnitRoomType,
                numOfGuests: this.newUnitNumOfGuests,
                numOfRooms: this.newUnitNumOfRooms,
                pricePerNight: this.newUnitPricePerNight,
                status: "ACTIVE",
                name: this.newUnitName,
                street: this.newUnitStreet,
                buildingNumber: this.newUnitBuildingNumber,
                city: this.newUnitCity,
                country: this.newUnitCountry,
                checkInTime: this.newUnitCheckInTime,
                checkOutTime: this.newUnitCheckOutTime,
                zipode: this.newUnitZipCode,
                newAmenities: this.newUnitAmenities,
                user: this.user,
                latitude: this.latitude,
                longitude: this.longitude
            }})
            .then(res => {
                if(res.data.name !== null){
                    this.closeNewUnitDialog();
                    this.addImagesModalDialogOpen = true;
                    this.createdUnit = res.data;
                } else {
                    alert("Failed to create unit!");
                }
            });
        }
        },

        uploadImages: function(){
            const fd = new FormData();
            fd.append("images", this.newUnitImageSources);
            axios.post('/rest/upload-images', fd, {headers: {"Content-Type": "multipart/form-data"}})
            .then(res => {
                if(res.data === true){
                    alert("Successfully created unit " + this.newUnitName + "!");
                    this.closeNewUnitDialog();
                } else {
                    alert("Failed to create unit!");
                }
            })
        },

/*
        createUnit: function(){
            alert("CREATEUNIT");
            this.tempId.prefix = "U";
            this.tempId.suffix = "0";

            this.tempApt.id = this.tempId;

            this.tempApt.name = this.newUnitName;
            this.tempApt.roomType = this.newUnitRoomType.toUpperCase();
            this.tempApt.numOfGuests = this.newUnitNumOfGuests;
            this.tempApt.numOfRooms = this.newUnitNumOfRooms;
            this.tempApt.pricePerNight = this.newUnitPricePerNight;
            this.tempApt.status = "ACTIVE";
            
            this.tempAddress.street =  this.newUnitStreet;
            this.tempAddress.city =  this.newUnitCity;
            this.tempAddress.zipCode =  this.newUnitZipCode;
            this.tempAddress.number =  this.newUnitNumber;
            this.tempAddress.country = this.newUnitCountry;
            this.tempAddress.isDeleted = false;
            
            this.tempLocation.address = this.tempAddress;
            this.tempLocation.longitude = "44.000";
            this.tempLocation.latitude = "44.000";
            this.tempLocation.isDeleted = false;

            this.tempApt.location = this.tempLocation;

            //this.tempApt.amenities = this.newUnitAmenities;
            this.splitAmenities = this.newUnitAmenities.split(",");
            for(amenity in this.splitAmenities){
                this.tempAmenity.description = amenity.trim();
                this.tempAmenity.value = true;
                this.tempAmenity.isDeleted = false;
                this.tempAmenities.push(this.tempAmenity);
            }
            this.tempApt.amenities = this.tempAmenities;
            this.tempApt.host = this.user;

            this.tempApt.checkinTime = this.newUnitCheckInTime;
            this.tempApt.checkOutTime = this.newUnitCheckOutTime;
            this.tempApt.imageSources = this.newImages;
            alert(this.newImages.length + " slika");

            this.tempApt.isDeleted = false;
            axios
            .post('/rest/create-new-unit', this.tempApt)
            .then(res => {
                if(res.data === true){
                    alert("Successfully created unit!");
                }
            });
        },
*/
        showComments: function(item){
            this.newComment = "";
            this.newGrade = 0;
            
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
                    if(res.data !== null){
                        alert("Unit deleted successfully!");
                        this.units = res.data;
                    } else {
                        alert("Failed to delete unit!");
                    }
                });
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
                        window.location.replace("index.html");
                    }
                });
            }
        },
    }
});