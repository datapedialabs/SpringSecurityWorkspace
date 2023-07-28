1) Configure callback url, generate app id and secret key in google in developer console 
https://console.developers.google.com
Login using gmail id:
ramesh@java2aspire.com
pwd: ****

Click on "New Project"
Project Name: Aspire Jbcp Calendar

Goto "Credentials"
	Expand Create credentials drop down list --> select "API Key"

OAuth consent screen:
Email address : ramesh@java2aspire.com
Product name shown to users : Aspire Jbcp Online

Client ID: 880727005003-klpr6i6d4rs4t3uiept91flr7ur82mbk.apps.googleusercontent.com
Client secret: MvfYBAmb0kvAnZBJfL50Tv5I

Create OAuth client ID:
Application type: Web Application
Authorized redirect URIs:
	https://localhost:8443/signin/google
	http://localhost:9090/signin/google
Click on "Ok"	

2) Configure App Id and App Secret Key in application.yml file
   spring:
     social:
        google:
     	 appId: 880727005003-klpr6i6d4rs4t3uiept91flr7ur82mbk.apps.googleusercontent.com
      	 appSecret: MvfYBAmb0kvAnZBJfL50Tv5I

3) Add google dependency in build.gradle file
   compile("org.springframework.social:spring-social-google:latest.release")

4) Add google button in login page
    #src/main/resources/templates/aspirelogin.html
		<br />
		<form th:action="@{/signin/google}" method="POST" class="form-horizontal">
		 	<input type="hidden" name="scope" value="profile" />
			<div class="form-actions">
				<input id="google-submit" class="btn" type="submit" value="Sign In With Google" />
			</div>
		</form>
		
5) Test url https://localhost:8443    

   
   