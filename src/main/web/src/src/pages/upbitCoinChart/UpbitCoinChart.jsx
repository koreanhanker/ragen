import React, { Component } from 'react';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

class UpbitCoinChart extends Component {
    constructor(props) {
        super(props);

        this.state = {
            series: [{ data: [] }],
            options: {
                chart: {
                    height: 350,
                    type: 'bar',
                    events: {
                        click: function (chart, w, e) {
                            // console.log(chart, w, e)
                        },
                    },
                },
                colors: [],
                plotOptions: {
                    bar: {
                        columnWidth: '45%',
                        distributed: true,
                    },
                },
                dataLabels: {
                    enabled: true,
                },
                legend: {
                    show: true,
                },
                xaxis: {
                    categories: [],
                    labels: {
                        style: {
                            colors: [],
                            fontSize: '12px',
                        },
                    },
                },
            },
        };

        this.fetchData = this.fetchData.bind(this);
    }


    componentDidMount() {
        this.fetchData();
        this.intervalId = setInterval(this.fetchData, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.intervalId);
    }

    fetchData = async () => {
        const market = 'KRW-XRP';
        const count = 1; // Ensure count is a valid positive integer

        try {
            const response = await axios.post('/example/candles', { market, count });
            const responseData = response.data;

            if (responseData.status === 'SUCCESS') {
                // Parse the JSON string contained in candlesList
                const data = JSON.parse(responseData.data.candlesList);

                if (Array.isArray(data) && data.length > 0) {
                    const candle = data[0];
                    const trade_price = candle.trade_price;
                    const opening_price = candle.opening_price;
                    const priceChangeColor = trade_price > opening_price ? '#FF0000' : trade_price < opening_price ? '#0000FF' : '#000000';
                    const newLabels  = {
                        style: {
                            colors: [priceChangeColor],
                            fontSize: '12px', // Ensure fontSize is set correctly
                        },
                    };

                    // Update state only if necessary
                    this.setState(prevState => {
                        const newData = [trade_price];
                        const newCategories = [market];
                        const newColors = [priceChangeColor];

                        // Correctly set the newLabels object
                        const newLabels = {
                            style: {
                                colors: [priceChangeColor],
                                fontSize: '12px',
                            },
                        };

                        // Update only if there is a change
                        if (
                            prevState.series[0].data[0] !== trade_price ||
                            prevState.options.xaxis.categories[0] !== market
                        ) {
                            return {
                                series: [{ data: newData }],
                                options: {
                                    ...prevState.options,
                                    colors: newColors,
                                    xaxis: {
                                        ...prevState.options.xaxis,
                                        categories: newCategories,
                                        labels: {
                                            ...prevState.options.xaxis.labels,
                                            style: newLabels.style, // Ensure this is not undefined
                                        },
                                    },
                                },
                            };
                        }
                        return null;
                    });
                } else {
                    console.log('No data available');
                }
            } else {
                console.error('API response status error:', responseData.message);
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };


    render() {
        return (
            <div>
                <div id="chart">
                    <ReactApexChart options={this.state.options} series={this.state.series} type="bar" height={350} />
                </div>
                <div id="html-dist"></div>
            </div>
        );
    }
}

export default UpbitCoinChart;
