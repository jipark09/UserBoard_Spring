<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>${param.bno}게시판 댓글</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<br>
<h2 class="h2">${param.bno}게시판 댓글 목록</h2>
<hr>
<div class="input-group mb-3">
    <span class="input-group-text" id="basic-addon1">댓글</span>
    <input type="text" class="form-control" name="comment" aria-label="Username" aria-describedby="basic-addon1">
    <br><br>
</div>
<%--댓글: <input type="text" name="comment"><br><br>--%>
<button id="sendBtn" type="button" class="btn btn-outline-success">댓글쓰기</button>
<button id="modBtn" type="button" class="btn btn-outline-warning">댓글수정</button>
<button id="goBoard" type="button" class="btn btn-outline-danger">돌아가기</button><br><br>

<div id="commentList"></div> <!--댓글 목록을 보여줌(ajax를 이용해서 불러올꺼임)-->

<div id="replyForm" style="display: none">
    <input type="text" name="replyComment">
    <button id="wrtRepBtn" type="button">답글등록</button>
</div>

<script>
    let bno = ${param.bno};

    $("#goBoard").on("click", function(){
        location.href="<c:url value='/board/read?bno=${param.bno}'/>";
    });

    // 댓글 가져오기 함수
    let showList = function (bno) {
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/JTI/comments?bno=' + bno,  // 요청 URI
            dataType : 'json', // 전송받을 데이터의 타입, 생략해도 됨: 기본이 json
            success : function(result) {
                // result가 오면 commentList에 담기
                // 댓글목록 가져온 것을 commmentList에 담게 됨
                // 들어오는 배열을 toHtml이라는 함수를 이용해서 <li>태그로 만든다음 그것을 commentList에 넣는다.
                $("#commentList").html(toHtml(result));
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()

    }

    // document 시작
    $(document).ready(function(){
        showList(bno);

        // 댓글 쓰기
        $("#sendBtn").click(function() {
            let comment = $("input[name=comment]").val();

            if(comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type: 'POST',
                url: '/JTI/comments?bno=' + bno,
                headers: {"content-type" : "application/json"},
                data : JSON.stringify({bno:bno, comment:comment}), // 전송할 데이터를 JSON으로!
                success : function (result) {
                    alert(result);
                    showList(bno); //  쓰기가 성공했을 때 보여줄꺼임
                },
                error : function () {alert("error")}
            });
        });

        // 답글 보여주기
        $("#commentList").on("click", ".replyBtn", function () {
            //1. replyForm을 li뒤에 옮긴다.
            $("#replyForm").appendTo($(this).parent());

            //2. 답글을 입력한 폼을 보여준다.
            $("#replyForm").css("display", "block");
        });

        // 답글 쓰기
        $("#wrtRepBtn").click(function() {
            let comment = $("input[name=replyComment]").val();
            let pcno = $("#replyForm").parent().attr("data-pcno");

            if(comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type: 'POST',
                url: '/JTI/comments?bno=' + bno,
                headers: {"content-type" : "application/json"},
                data : JSON.stringify({pcno:pcno, bno:bno, comment:comment}), // 부모 댓글이 누군지 적어줘야 함
                success : function (result) {
                    alert(result);
                    showList(bno); //  쓰기가 성공했을 때 보여줄꺼임
                },
                error : function () {alert("error")}
            });

            // 답글 칸이 다시 안보이게 해야함
            $("#replyForm").css("display", "none");
            // 값도 비워야함
            $("input[name=replyComment]").val('');
            // 원래 위치로 되돌려 놔야함: body 아래에 위치
            $("#replyForm").appendTo("body");

        });
        
        // 삭제
        $("#commentList").on("click", ".delBtn", function () {
            // li가 버튼의 부모
            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");

            $.ajax({
                type: 'DELETE',
                url: '/JTI/comments/' + cno + '?bno=' + bno,
                success : function (result) {
                    alert(result)
                    // 삭제된 다음에 새로 갱신되어야 함
                    showList(bno);
                }
            });
        });

        // 수정 보여주기
        $("#commentList").on("click", ".modBtn", function () {
            let cno = $(this).parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent()).text();

            //1. comment의 내용을 input에 뿌려주기
            $("input[name=comment]").val(comment);
            //2. cno 번호를 전달하기
            $("#modBtn").attr("data-cno", cno);

        })

        // 수정 쓰기
        $("#modBtn").click(function() {
            let comment = $("input[name=comment]").val();
            let cno = $(this).attr("data-cno");

            if(comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type: 'PATCH',
                url: '/JTI/comments/' + cno,
                headers: {"content-type" : "application/json"},
                data : JSON.stringify({cno:cno, comment:comment}), // 전송할 데이터를 JSON으로!
                success : function (result) {
                    alert(result);
                    showList(bno); //  수정이 성공했을 때 보여줄꺼임
                },
                error : function () {alert("error")}
            });
        });
    });

    // 배열들어온 것을 <li>태그를 이용해서 전체 <ul>를 구성한 다음에 그것을 넣을 것이다.
    let toHtml = function (comments) {
        let tmp = "<ul class='list-group'>";

        // 댓글 하나하나 들고와서 tmp에 쌓는다.
        comments.forEach(function (comment) {
            tmp += '<li class=list-group-item data-cno=' + comment.cno;
            tmp += ' data-pcno=' + comment.pcno;
            tmp += ' data-bno=' + comment.bno + '>';

        // cno와 pcno가 다르다는 것은 답글이라는 것임!
        if(comment.cno != comment.pcno) {
            tmp += 'ㄴ';
        }
            // span태그에 넣어야 나중에 작성자만 따로 읽어오기 쉽다.
            tmp += ' ID: <span class="commenter">' + comment.commenter + '</span>';
            tmp += ' 내용: <span class="comment">' + comment.comment + '</span>&nbsp;';
            // tmp += ' up_date=' + new Date(comment.up_date);
            tmp += '<button class="delBtn">삭제</button>&nbsp;';
            tmp += '<button class="modBtn">수정</button>&nbsp;';
            tmp += '<button class="replyBtn">답글</button>&nbsp;';
            tmp += '</li>';
        })

        return tmp + "</ul>"; // ul을 html로 반환한다.
    }
</script>
</body>
</html>