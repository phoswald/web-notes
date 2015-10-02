
function checkLogin() {
    $.ajax({
        type: "GET",
        url: "rest/users/current",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        window.location.href = "notes.html";
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#login_wait").hide();
        $("#login_dialog").show();
    });
}

function login() {
    var data = { 
            userId: $("#username").val(),
            password: $("#password").val()
        };
    $.ajax({
        type: "POST",
        url: "rest/users/login",
        data: JSON.stringify(data),
        contentType: "application/json; charset=UTF-8" })
    .done(function(data, textStatus, jqXHR) {
        window.location.href = "notes.html";
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#login_denied").show();
    });
}

$(document).ready(function() {
    $("#login_wait").show();
    $("#login_denied").hide();
    $("#login_dialog").hide();
    $("#login_button").click(login);
    checkLogin(); // window.setTimeout(checkLogin, 1000);
});
