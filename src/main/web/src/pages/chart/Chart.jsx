import React, { Component } from 'react';
import ReactApexChart from "react-apexcharts";

class Chart extends Component {
    constructor(props) {
        super(props);

        this.state = {
            series: [
                {
                    name: "Funnel Series",
                    data: [1380, 1100, 990, 880, 740, 548, 330, 200],
                },
            ],
            options: {
                chart: {
                    type: 'bar',
                    height: 350,
                },
                plotOptions: {
                    bar: {
                        borderRadius: 0,
                        horizontal: true,
                        barHeight: '80%',
                        isFunnel: true,
                    },
                },
                dataLabels: {
                    enabled: true,
                    formatter: function (val, opt) {
                        return opt.w.globals.labels[opt.dataPointIndex] + ':  ' + val;
                    },
                    dropShadow: {
                        enabled: true,
                    },
                },
                title: {
                    text: 'Recruitment Funnel',
                    align: 'middle',
                },
                xaxis: {
                    categories: [
                        'Sourced',
                        'Screened',
                        'Assessed',
                        'HR Interview',
                        'Technical',
                        'Verify',
                        'Offered',
                        'Hired',
                    ],
                },
                legend: {
                    show: false,
                },
            },
        };
    }

    render() {
        return (
            <div id="chart">
                <ReactApexChart options={this.state.options} series={this.state.series} type="bar" height={350} />
            </div>
        );
    }
}

export default Chart;
