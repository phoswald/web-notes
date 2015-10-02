
function checkLogin() {
    $.ajax({
        type: "GET",
        url: "rest/users/current",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        $("#current_user").text("Logged in as: " + data.name + " (" + data.userId + ")");
    })    
    .fail(function(jqXHR, textStatus, errorThrown) {
        window.location.href = "login.html";
    });
}

function logout() {
    $.ajax({
        type: "POST",
        url: "rest/users/logout" })
    .done(function(data, textStatus, jqXHR) {
        window.location.href = "login.html";
    })    
}

$(document).ready(function() {
    // ...
    checkLogin();
});
