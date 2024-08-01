import React from "react";
import { Routes, Route, useNavigate } from "react-router-dom";
import Test from "./pages/test/Test";
import ShowChat from "./pages/ShowChat/ShowChat";
import Chart from "./pages/chart/Chart";
import Close from "./pages/close/Close";
import QueryList from "./pages/queryList/QueryList";
function App() {
    const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동 함수를 가져옵니다.

    const buttonStyle = {
        margin: "8px" // 버튼 간격을 8px로 설정
    };

    // 버튼 클릭 시 페이지 이동을 처리하는 함수
    const handleButtonClick = (to) => {
        navigate(to); // 페이지 이동 함수를 호출하여 to 경로로 이동
    };

    return (
        <div className="App">
            <nav>
                <button onClick={() => handleButtonClick('/test')}>DB연동(Spring boot 3.1)</button>
                <button style={buttonStyle} onClick={() => handleButtonClick('/showChat')}>채팅(Stomp Socket)</button>
                <button style={buttonStyle} onClick={() => handleButtonClick('/chart')}>차트(Apexcharts)</button>
                <button style={buttonStyle} onClick={() => handleButtonClick('/close')}>닫기(Close 경고)</button>
                <button style={buttonStyle} onClick={() => handleButtonClick('/queryList')}>쿼리(queryList)</button>
            </nav>
            <Routes>
                <Route path="/test" element={<Test />} />
                <Route path="/showChat" element={<ShowChat />} />
                <Route path="/chart" element={<Chart />} />
                <Route path="/close" element={<Close />} />
                <Route path="/queryList" element={<QueryList />} />
            </Routes>
        </div>
    );
}

export default App;
