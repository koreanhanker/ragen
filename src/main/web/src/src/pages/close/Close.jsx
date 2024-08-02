import React, { useState, useEffect } from 'react';

function Close() {
    const [unsavedChanges, setUnsavedChanges] = useState(false);


    useEffect(() => {
        const handleBeforeUnload = (e) => {
            if (unsavedChanges) {

                e.returnValue =  '변경사항이 저장되지 않을 수 있습니다. 정말로 페이지를 떠나시겠습니까?';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [unsavedChanges]);

    const handleChangeUnsavedChanges = (hasChanges) => {
        setUnsavedChanges(hasChanges);
    };



    return (
        <div>
            <h1>크롬 창 닫기 커스터마이징</h1>
            <p>변경 사항이 있는지 여부를 설정하고 커스텀 메시지를 입력하세요.</p>

            <button onClick={() => handleChangeUnsavedChanges(true)}>변경 사항 있음</button>
            <button onClick={() => handleChangeUnsavedChanges(false)}>변경 사항 없음</button>
        </div>
    );
}

export default Close;
