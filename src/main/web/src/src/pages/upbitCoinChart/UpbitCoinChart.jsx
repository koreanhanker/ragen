import React, { Component } from 'react';
import ReactApexChart from 'react-apexcharts';

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

        this.intervalId = null;
    }

    componentDidMount() {
        this.fetchData();
        this.intervalId = setInterval(this.fetchData, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.intervalId);
    }

    fetchData = () => {
        const market = 'KRW-XRP';
        const count = 1;
        const url = `https://api.upbit.com/v1/candles/days?market=${market}&count=${count}`;

        fetch(url, {
            method: 'GET',
            headers: {
                accept: 'application/json',
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (data && data.length > 0) {
                    const candle = data[0];
                    const trade_price = candle.trade_price;
                    const opening_price = candle.opening_price;
                    const priceChangeColor = trade_price > opening_price ? '#FF4560' : trade_price < opening_price ? '#0000FF' : '#000000';

                    this.setState(prevState => {
                        if (prevState.series[0].data[0] !== trade_price) {
                            return {
                                series: [{ data: [trade_price] }],
                                options: {
                                    ...prevState.options,
                                    colors: [priceChangeColor],
                                    xaxis: {
                                        ...prevState.options.xaxis,
                                        categories: [market],
                                        labels: {
                                            style: {
                                                colors: [priceChangeColor],
                                                fontSize: '12px',
                                            },
                                        },
                                    },
                                },
                            };
                        }
                        return null;
                    });
                } else {
                    console.log('No data available');
                    // Handle case with no data if necessary
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                // Handle errors, such as by showing a message to the user
            });
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
