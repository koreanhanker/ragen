import React from "react";
import { Routes, Route, useNavigate } from "react-router-dom";
import Test from "./pages/test/Test";
import ShowChat from "./pages/ShowChat/ShowChat";
import Chart from "./pages/chart/Chart";
import Close from "./pages/close/Close";
import QueryList from "./pages/queryList/QueryList";
import UpbitCoinChart from "./pages/upbitCoinChart/UpbitCoinChart";

function App() {
    const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동 함수를 가져옵니다.

    // select 박스의 선택값에 따라 페이지 이동을 처리하는 함수
    const handleSelectChange = (event) => {
        const value = event.target.value;
        navigate(value); // 페이지 이동 함수를 호출하여 선택된 경로로 이동
    };

    return (
        <div className="App">
            <nav>
                <select onChange={handleSelectChange} defaultValue="">
                    <option value="" disabled>선택하세요</option>
                    <option value="/test">DB연동(Spring boot 3.1)</option>
                    <option value="/showChat">채팅(Stomp Socket)</option>
                    <option value="/chart">차트(Apexcharts)</option>
                    <option value="/close">닫기(Close 경고)</option>
                    <option value="/queryList">쿼리(queryList)</option>
                    <option value="/upbitCoinChart">업비트코인정보(upbitCoinChart)</option>
                </select>
            </nav>
            <Routes>
                <Route path="/test" element={<Test />} />
                <Route path="/showChat" element={<ShowChat />} />
                <Route path="/chart" element={<Chart />} />
                <Route path="/close" element={<Close />} />
                <Route path="/queryList" element={<QueryList />} />
                <Route path="/upbitCoinChart" element={<UpbitCoinChart />} />
            </Routes>
        </div>
    );
}

export default App;
