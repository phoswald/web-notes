
function checkLogin() {
    $.ajax({
        type: "GET",
        url: "rest/users/current",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        window.location.href = "notes.html";
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#loading").hide();
        $("#ready").show();
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
        // Note: jQuery's $("#login_denied").show() does not work for <paper-toast>.
        document.querySelector("#login_denied").show();
    });
}

$(document).ready(function() {
    // Note: jQuery's $(...).show() and hide() only works for <div>, but not for <paper-material>.
    $("#loading").show();
    $("#ready").hide();
    $("#login_button").click(login);
    // checkLogin();
    window.setTimeout(checkLogin, 1000);
});
