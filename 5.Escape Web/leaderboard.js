window.addEventListener('DOMContentLoaded', function()
{
    var currentPage = window.location.pathname.split("/").pop();
    if( currentPage !== "mainpage.html"&& currentPage != "mainpage_logined.html" && currentPage != "1.html") //메인페이지일 경우는 기록 갱신 안함
        recordUpdate();
    if(currentPage == "mainpage_logined.html")
        clear();

});
function recordUpdate() {
    let currentUser = localStorage.getItem("id");
    let currentStage = window.location.pathname.split("/").pop().split(".")[0];
    if(!currentUser)
        return //로그인 안되어있으면 기록을 업데이트하지 않는다.

    // 기존 기록 가져오기
    let existingRecord = localStorage.getItem(currentUser + "End");
    

    // 기록이 없거나 높은 스테이지로 갱신할 경우에만 업데이트
    if (!existingRecord || parseInt(currentStage) > parseInt(existingRecord)) {
        localStorage.setItem(currentUser + "End", parseInt(currentStage) - 1);
    }       
}
function clear(){
    let currentUser = localStorage.getItem("id");
    let existingRecord = localStorage.getItem(currentUser + "End");

    if(existingRecord == 11)
        localStorage.setItem(currentUser + "End", 12);
}

// 리더보드 표시 함수
function showLeaderboard() {
    let leaderboardContainer = document.getElementById("leaderboard-container");

    // 로컬 스토리지에서 키에 End가 포함된 항목 찾기
    let leaderboardData = [];
    for (let i = 0; i < localStorage.length; i++) {
        let key = localStorage.key(i);
        if (key.includes("End")) {
            let userId = key.split("End")[0];
            let pageName = localStorage.getItem(key);
            leaderboardData.push({ nickname: userId, stage: parseInt(pageName) }); //parseInt = String을 Int형으로 변환
        }
    }

    // 기록이 없을 경우 안내 메시지 표시
    if (leaderboardData.length === 0) {
        leaderboardContainer.innerHTML = "<h2>리더보드</h2> 플레이 기록이 없어 <br> 리더보드가 비었습니다. <br> 플레이하여 첫 기록을 남기세요.";
        return;
    }

    //정렬
    leaderboardData.sort((a, b) => {
        if (a.stage === b.stage) {
            return a.nickname.localeCompare(b.nickname);
        }
        return b.stage - a.stage;
    });

    // 리더보드를 HTML에 삽입
    let leaderboardHTML = "<h2>리더보드</h2><ol>";
    let rank = 1;
    for (let i = 0; i < leaderboardData.length; i++) {
        leaderboardHTML += `<li>${rank}위. ${leaderboardData[i].nickname} - ${leaderboardData[i].stage } 스테이지</li>`;
        // 값이 같으면 같은 순위로 표시
        if (i < leaderboardData.length - 1 && leaderboardData[i].stage !== leaderboardData[i + 1].stage) { 
            rank++; // 다음 값이 있고 다음 값이 지금 값과 같으면 공동이므로 숫자를 늘리지 않는다
        }
    }
    leaderboardHTML += "</ol>";

    // 리더보드를 HTML에 삽입
    leaderboardContainer.innerHTML = leaderboardHTML;
}