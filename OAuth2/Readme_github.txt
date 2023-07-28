1) Configure callback url, generate app id and secret key in github in developer console 
https://github.com/
Login to github:
username: ramesh@java2aspire.com
pwd: ****

Goto Settings --> Developer Settings
Click on "New OAuth App"

Application Name: Aspire Jbcp Calendar
Home Page URL: http://www.java2aspire.com

Authorization callback URL: https://localhost:8443/signin/github

Click on "Register application"

Client ID: 6bf2445e85dfb3d02264
Client Secret: 1972bf93d76af08704e9b8daf211b12b0bde190e


2) Configure App Id and App Secret Key in application.yml file
   spring:
     social:
       github:
          appId: 6bf2445e85dfb3d02264
          appSecret: 1972bf93d76af08704e9b8daf211b12b0bde190e

3) Add github dependency in build.gradle file
   compile("org.springframework.social:spring-social-github:latest.release")

4) Add github button in login page
    #src/main/resources/templates/aspirelogin.html
		<br />
		<form th:action="@{/signin/github}" method="POST" class="form-horizontal">
		 	<input type="hidden" name="scope" value="public_profile" />
			<div class="form-actions">
				<input id="github-submit" class="btn" type="submit" value="Sign In With Github" />
			</div>
		</form>
		
5) Test url https://localhost:8443    

   
   