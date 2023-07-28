1) Configure callback url, generate app id and secret key in linked in developer console  
https://developer.linkedin.com/
MyApps
Click on "Create Application"
Company Name: Aspire Technologies Inc.
Application Name: Aspire Jbcp Calendar
Application Description: Jbcp calendar
Application Logo: 80*80 pixel
Application Use: Education Training Certification
website url: http://www.java2aspire.com
business url: ramesh@aspirecareers.in
Business Phone: 9885407683
Click on "Submit" button

Client ID:	818x6t85s1vj1y
Client Secret:	UGC0tbztNNvPgNZq


Authorized Redirect URLs: 
	https://127.0.0.1:8443/signin/linkedin
	http://127.0.0.1:9090/signin/linkedin
	http://192.168.1.3:9090/signin/linkedin
	https://192.168.1.3:8443/signin/linkedin

click on "Update"

2) Configure App Id and App Secret Key in application.yml file
   spring:
     social:
       linkedin:
         app-id: 818x6t85s1vj1y
         app-secret: UGC0tbztNNvPgNZq

3) Add linked in dependency in build.gradle file
   compile("org.springframework.boot:spring-boot-starter-social-linkedin")

4) Add LinkedIn button in login page
    #src/main/resources/templates/aspirelogin.html
    <br />
		<form th:action="@{/signin/linkedin}" method="POST" class="form-horizontal">
			<!-- <input type="hidden" name="scope" value="public_profile" /> -->
			<div class="form-actions">
				<input id="linkedin-submit" class="btn" type="submit" value="Sign In With LinkedIn" />
			</div>
		</form>
		
5) Test url https://127.0.0.1:8443    
   