1) Configure app id and secret key in facebook in developer console 
https://developers.facebook.com/
Login to facebook
username: rkandepu@rediffmail.com
pwd: ******

My Apps --> Add New App
Display Name: Jbcp Calendar Online
Contact Email: rkandepu@rediffmail.com
Click on "Create App ID"
Settings --> click on "Basic"

API ID	    : 417927345361441
App Secrect : b82500f3187048368cf50e73f5f88289

Note: No callback urls.

2) Configure App Id and App Secret Key in application.yml file
   spring:
       social:
   		 # facebook
    	 facebook:
      		appId: 417927345361441
      		appSecret: b82500f3187048368cf50e73f5f88289

3) Add facebook dependency in build.gradle file, if not already added
   //compile("org.springframework.boot:spring-boot-starter-social-facebook")
    compile("org.springframework.social:spring-social-facebook:3.0.0.M1")

4) Add Facebook button in login page
    #src/main/resources/templates/aspirelogin.html
		<br />
		<form th:action="@{/signin/facebook}" method="POST" class="form-horizontal">
			<input type="hidden" name="scope" value="public_profile" />
			<div class="form-actions">
				<input id="facebook-submit" class="btn" type="submit" value="Sign In With Facebook" />
			</div>
		</form>
		
5) Test url https://localhost:8443    

   
   