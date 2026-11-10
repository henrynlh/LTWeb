<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body class="thank-page">
    <h1>Thanks for joining our email list</h1>
    <p>Here is the information that you entered:</p>

    <label>Email:</label>
    <span>${user.email}</span><br>

    <label>First Name:</label>
    <span>${user.firstName}</span><br>

    <label>Last Name:</label>
    <span>${user.lastName}</span><br>

    <label>Date of Birth:</label>
    <span>${user.birthDate}</span><br>

    <label>Heard about us from:</label>
    <span>${survey.hearAboutUs}</span><br>

    <label>Contact me by:</label>
    <span>${survey.contactMethod}</span><br>

    <label>Your responses for receive announcements:</label>
    <span>${survey.receiveAnnouncements ? 'Yes' : 'No'}</span><br>

    <p>To enter another email address, click on the Back
       button in your browser or the Return button shown
       below.</p>

    <form action="" method="get">
        <input type="hidden" name="action" value="join">
        <input type="submit" value="Return">
    </form>

</body>
</html>
