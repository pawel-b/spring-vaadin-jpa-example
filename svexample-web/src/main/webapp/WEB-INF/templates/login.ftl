<!DOCTYPE html>
<html lang="pl">
  <head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <style type="text/css">
      /* Override some defaults */
      html, body {
        background-color: #eee;
      }
      body {
        padding-top: 40px; 
      }
      .container {
        width: 400px;
      }

      /* The white background content wrapper */
      .container > .content {
        background-color: #fff;
        padding: 20px;
        margin: 0 -20px; 
        -webkit-border-radius: 10px 10px 10px 10px;
           -moz-border-radius: 10px 10px 10px 10px;
                border-radius: 10px 10px 10px 10px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                box-shadow: 0 1px 2px rgba(0,0,0,.15);
      }

      .login-form {
        margin-left: 50px;
        margin-right: 50px;
      }
    
      legend {
        margin-right: -50px;
        font-weight: bold;
        color: #404040;
      }

    </style>

</head>
<body>
  <div class="container">
    <div class="content">
      <div class="row">
        <div class="login-form">
          <h2><@spring.message "loginPage.title"/></h2>
          <form action="/svexample-web/j_spring_security_check" method="POST">
            <fieldset>
              <div class="form-group">
                <input class="form-control" type="text" id="j_username" name="j_username" value='jimi'>
              </div>
              <div class="form-group">
                <input class="form-control" type="password" id="j_password" name="j_password" value="jimispassword">
              </div>
              <div class="form-group pull-right">
                  <button class="btn btn-primary" type="submit"><@spring.message "loginPage.signin"/></button>
              </div>
            </fieldset>
          </form>
        </div>
      </div>
    </div>
	<div class="row">
		Use jimmi jimispassword (manager)
	</div>
	<div class="row">
	    or bob bobspassword (user)
	</div>
    <div>
    </div>
    <#if isError?? && isError?string == "true">
      <div class="control-group">
        <div class="alert alert-danger"><@spring.message "loginPage.loginError"/></div>
      </div>
    </#if>    
  </div> <!-- /container -->
</body>
</html>
