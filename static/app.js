const Login = {template: '<login></login>'}
const Home = {template: '<home></home>'}
const SearchResults = {template: '<search></search>'}

const routes = [
  { path: '/login', component: Login},
  { path: '/', component: Home},
  { path: '/home', component: Home},
  { path: '/search-results', component: SearchResults}
]
Vue.use(VueRouter)
const router = new VueRouter({
  routes 
})

var app = new Vue({
  router,
  el: '#app',
});