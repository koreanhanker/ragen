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
        // Define query parameters
        const market = 'KRW-XRP';
        const count = 1;

        // Construct the URL with query parameters
        const url = `https://api.upbit.com/v1/candles/days?market=${market}&count=${count}`;

        const options = {
            method: 'GET',
            headers: {
                accept: 'application/json',
            },
        };

        fetch(url, options)
            .then(response => response.json())
            .then(data => {
                console.log('API Response:', data); // Debugging: Log API response

                if (data && data.length > 0) {
                    const candle = data[0];
                    const trade_price = candle.trade_price;
                    const opening_price = candle.opening_price;
                    const priceChangeColor = trade_price > opening_price ? '#FF4560' : trade_price < opening_price ? '#0000FF' : '#000000';

                    this.setState({
                        series: [{ data: [trade_price] }],
                        options: {
                            ...this.state.options,
                            colors: [priceChangeColor],
                            xaxis: {
                                ...this.state.options.xaxis,
                                categories: [market],
                                labels: {
                                    style: {
                                        colors: [priceChangeColor],
                                        fontSize: '12px',
                                    },
                                },
                            },
                        },
                    }, () => {
                        console.log('State Updated:', this.state); // Debugging: Log state after update
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
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
