import React, { Component } from 'react';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

class UpbitCoinChart extends Component {
    constructor(props) {
        super(props);
        const colors = ['#008FFB', '#00E396', '#FEB019', '#FF4560', '#775DD0', '#3F51B5', '#546E7A', '#D4526E', '#8D5B4C', '#F86624'];

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
                colors: colors,
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
                            colors: colors,
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
        const url = 'https://api.upbit.com/v1/candles/minutes/1?market=KRW-XRP&count=1';

        axios.get(url, {
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                const data = response.data;
                if (data && data.length > 0) {
                    const prices = data.map(candle => candle.trade_price);
                    const categories = data.map(candle => candle.market);

                    this.setState({
                        series: [{ data: prices }],
                        options: {
                            ...this.state.options,
                            xaxis: {
                                ...this.state.options.xaxis,
                                categories: categories,
                            },
                        },
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
