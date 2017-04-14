$("#submit-btn").on("click", addAnswer);
function addAnswer(e) {
    e.preventDefault();
    let queryString = $("form[name=answer]").serialize();
    $.ajax({
        type : 'POST',
        url : '/api/qna/addanswer',
        data : queryString,
        dataType : 'json',
        error: function() {
            console.log("error!");
        },
        success : function(data, status) {
            console.log("status: ", status);
            let answerTemplate = $("#answerTemplate").html();
            let template = answerTemplate.format(data.writer, new Date(data.createdDate), data.contents, data.answerId, data.answerId);
            $(".qna-comment-slipp-articles").prepend(template);
        }
    });
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

