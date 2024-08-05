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
                    formatter: (val, opts) => {
                        const percentageChange = this.state.percentageChange;
                        return `${val}(${percentageChange ? percentageChange.toFixed(2) : 0}%)`;
                    },
                    style: {
                        colors: ['#FFFFFF'], // Set text color to white
                        fontSize: '12px',
                    },
                },
                legend: {
                    show: true,
                },
                xaxis: {
                    categories: [],
                    labels: {
                        style: {
                            colors: [], // Set text color to white
                            fontSize: '12px',
                        },
                    },
                },
                yaxis: {
                    labels: {
                        style: {
                            colors: [], // Set text color to white
                            fontSize: '12px',
                        },
                    },
                },
            },
            percentageChange: null,
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
        const count = 1;

        try {
            const response = await axios.post('/example/candles', { market, count });
            const responseData = response.data;

            if (responseData.status === 'SUCCESS') {
                const data = JSON.parse(responseData.data.candlesList);

                if (Array.isArray(data) && data.length > 0) {
                    const candle = data[0];
                    const trade_price = candle.trade_price;
                    const opening_price = candle.opening_price;
                    const percentageChange = ((trade_price - opening_price) / opening_price) * 100;
                    const priceChangeColor = trade_price > opening_price ? '#FF0000' : trade_price < opening_price ? '#0000FF' : '#000000';

                    this.setState(prevState => {
                        const newData = [trade_price];
                        const newCategories = [market];
                        const newColors = [priceChangeColor];

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
                                        style: {
                                            colors: [priceChangeColor],
                                            fontSize: '12px',
                                        },
                                    },
                                },
                                dataLabels: {
                                    ...prevState.options.dataLabels,
                                    style: {
                                        colors: ['#FFFFFF'], // Set text color to white
                                        fontSize: '12px',
                                    },
                                },
                            },
                            percentageChange,
                        };
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
