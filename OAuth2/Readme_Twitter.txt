1) Configure callback url, app id and secret key in twitter by using developer console 
https://apps.twitter.com/ 
Click on "Apply for developer account"
click on "Continue"
Choose "I am requesting access for my personal use" button
Account Name: Aspire Jbcp Calendar
Primary country of operation: India
What use case(s) are you interested in? Academic research

Application under review.
You'll receive an email when the review will be completed. 

Goto Developer console by using https://developer.twitter.com/

Aspire Jbcp Calendar --> Apps --> Click on "Create an App" button
App Name: Jbcp Calendar Online
Description: Jbcp Calender Application
Website URL: https://www.java2aspire.com
Callback URLs: https://localhost:8443/signin/twitter
Tell us how this app will be used:  write minimum 100 characters description
Click on "Create" button

Consumer Key (API Key):	EMA01A3k9M7NlUbQPMDlYPHpx
Consumer Secret (API Secret):	klF4BZD6jjmHZLlgJvrNzR83oFgW8hiLkwY0pLxfdAqnbUTE1X

2) Configure App Id and App Secret Key in application.yml file
   spring:
     social:
       twitter:
         appId: EMA01A3k9M7NlUbQPMDlYPHpx
         appSecret: klF4BZD6jjmHZLlgJvrNzR83oFgW8hiLkwY0pLxfdAqnbUTE1X

3) Add twitter dependency in build.gradle file, if not already added
   compile("org.springframework.boot:spring-boot-starter-social-twitter")

4) Add Twitter button in login page
    #src/main/resources/templates/aspirelogin.html
		<hr />
		<h3>Social Login</h3>
		<br />
		<form th:action="@{/signin/twitter}" method="POST" class="form-horizontal">
			<input type="hidden" name="scope" value="public_profile" />
			<div class="form-actions">
				<input id="twitter-submit" class="btn" type="submit" value="Sign In With Twitter" />
			</div>
		</form>
		
5) Test website by using https://localhost:8443    

   
   