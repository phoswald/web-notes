var templateNotes = null;

function compile() {
    templateNotes = Hogan.compile($("#template_notes").text());
}

function checkLogin() {
    $.ajax({
        type: "GET",
        url: "rest/users/current",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        $("#current_user").text("Logged in as: " + data.name + " (" + data.userId + ")");
        query();
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

function query() {
    $.ajax({
        type: "GET",
        url: "rest/notes/list",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        //$("#status").text("found " + data.length + " items");
        $("#notes").html(templateNotes.render({ notes: data }));
        $(".remove_button").click(function() { remove($(this).attr("data-id")); });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#query_errormsg").text(errorThrown);
        document.querySelector("#query_failed").show();
    });    
}

function store() {
    var note = {
        name: $("#store_name").val(),
        content: $("#store_content").val()
    };
    $.ajax({
        type: "POST",
        url: "rest/notes/add",
        data: JSON.stringify(note),
        contentType: "application/json; charset=UTF-8" })
    .done(function(data, textStatus, jqXHR) {
        $("#store_name").val("");
        $("#store_content").val("");
        document.querySelector("#store_successful").show();
        query();
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#store_errormsg").text(errorThrown);
        document.querySelector("#store_failed").show();
    });    
}

function remove(noteId) {
    $.ajax({
        type: "DELETE",
        url: "rest/notes/single/" + noteId })
    .done(function(data, textStatus, jqXHR) {
        document.querySelector("#remove_successful").show();
        query();
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#remove_errormsg").text(errorThrown);
        document.querySelector("#remove_failed").show();
    });
}

$(document).ready(function() {
    compile();
    $("#query_button").click(query);
    $("#store_button").click(store);
    checkLogin();
});
