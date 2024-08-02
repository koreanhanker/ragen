import React, { useState, useEffect } from 'react';
import axios from 'axios';
const ListComp = ({ data }) => {
    if (!data || data.length === 0) {
        return <li>No data available</li>;
    }

    const keys = Object.keys(data[0]); // Assuming all items have the same keys

    return (
        <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '16px' }}>
                <thead>
                <tr>
                    {keys.map((key, index) => (
                        <th key={index} style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>
                            {key}
                        </th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {data.map((item, rowIndex) => (
                    <tr key={rowIndex}>
                        {keys.map((key, colIndex) => (
                            <td key={colIndex} style={{ border: '1px solid #ddd', padding: '8px' }}>
                                {item[key]}
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

const QueryList = () => {
    const [data, setData] = useState(null); // Use null as the initial state
    const [query, setQuery] = useState('');

    const fetchData = async () => {
        console.log("query :" + query);
        try {
            const response = await axios.post('/example/getQueryList', { query });
            setData(response.data.data.queryList);
            console.log(response.data.data.queryList);
        } catch (error) {
            console.error('Error fetching data:', error);
            setData([]); // Set an empty array or another appropriate value in case of an error
        }
    };

    const handleQueryChange = (event) => {
        setQuery(event.target.value);
    };

    const handleFetchData = () => {
        fetchData();
    };

    useEffect(() => {
        fetchData(); // Fetch data on initial render
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <div>
            <h1>Data List</h1>
            <div>
                <label htmlFor="queryInput">Enter Query:</label>
                <input
                    type="text"
                    id="queryInput"
                    value={query}
                    onChange={handleQueryChange}
                />
                <button onClick={handleFetchData}>Fetch Data</button>
            </div>
            <ul>
                {data !== null && data.length > 0 ? (
                    <ListComp data={data} />
                ) : (
                    <li>No data available</li>
                )}
            </ul>
        </div>
    );
};

export default QueryList;

