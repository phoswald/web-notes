var templateGreetings = null;

function compile() {
    templateGreetings = Hogan.compile($("#template-greetings").text());
}

function query() {
    $("#status").text("querying");
    $.ajax({
        type: "GET",
        url: "rest/service/greetings",
        dataType: "json" })
    .done(function(data, textStatus, jqXHR) {
        $("#status").text("found " + data.length + " items");
        $("#greetings").html(templateGreetings.render({ greetings: data }));
        $(".del-button").click(function() { del($(this).attr("data-id")); });
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
        $("#list").text("");
    });
}

function add() {
    $("#status").text("adding");
    var greeting = {
        name: $("#add-name").val(),
        text: $("#add-text").val()
    };
    $.ajax({
        type: "POST",
        url: "rest/service/greetings",
        data: JSON.stringify(greeting),
        contentType: "application/json; charset=UTF-8" })
    .done(function(data, textStatus, jqXHR) {
        $("#add-name").val("");
        $("#add-text").val("");
        $("#status").text("added");
        query();
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
    });
}

function del(id) {
    $("#status").text("deleting");
    $.ajax({
        type: "DELETE",
        url: "rest/service/greeting/" + id })
    .done(function(data, textStatus, jqXHR) {
        $("#del-id").val("");
        $("#status").text("deleted");
        query();
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        $("#status").text("failed: " + errorThrown);
    });
}

$(document).ready(function() {
    compile();
    $("#query-button").click(query);
    $("#add-button").click(add);
    $("#status").text("ready");
    query();
});
