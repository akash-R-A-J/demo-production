// Example data for the pie chart
const pieChartData = {
    labels: ['Form', 'Meeting', 'OTP', 'Others'],
    datasets: [{
        data: [13, 17, 5, 65],
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#D3D3D3'],
    }],
};

// Get the canvas element
const pieChartCanvas = document.getElementById('emailPieChart').getContext('2d');

// Create the pie chart using Chart.js
const emailPieChart = new Chart(pieChartCanvas, {
    type: 'pie',
    data: pieChartData,
    options: {
        plugins: {
            datalabels: {
                color: '#808080', // Data label text color
                formatter: (value, ctx) => {
                    // Display percentage with one decimal place
                    return `${value.toFixed(1)}%`;
                },
                font: {
                    weight: 'bold',
                    size: 15,
                },
            },
        },
    },
});

// Function to add emails to the respective category column
function addEmailsToCategoryColumn(category, emails) {
    const categoryColumn = document.getElementById(`${category}-category`);

    // Clear existing content
    categoryColumn.innerHTML = `<h2>${capitalizeFirstLetter(category)} Category</h2>`;

    // Add emails to the category column
    emails.forEach(email => {
        const emailInfo = document.createElement('p');
        emailInfo.textContent = `${email.subject} - Arrived on ${email.arrivalDate}`;
        categoryColumn.appendChild(emailInfo);
    });
}

// Add example emails to respective category columns
const exampleEmails = [
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    { subject: 'Form Submission', category: 'form', arrivalDate: '2023-02-10' },
    { subject: 'Meeting Invitation', category: 'meeting', arrivalDate: '2023-02-09' },
    { subject: 'OTP Verification', category: 'otp', arrivalDate: '2023-02-08' },
    // ... more emails
];

addEmailsToCategoryColumn('form', exampleEmails.filter(email => email.category === 'form'));
addEmailsToCategoryColumn('meeting', exampleEmails.filter(email => email.category === 'meeting'));
addEmailsToCategoryColumn('otp', exampleEmails.filter(email => email.category === 'otp'));

// Helper function to capitalize the first letter of a string
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// Additional code for showing percentage all the time
Chart.plugins.register({
    afterDatasetsDraw: function(chart, easing) {
        // To only draw at the end of animation, check for easing === 1
        var ctx = chart.ctx;

        chart.data.datasets.forEach(function(dataset, i) {
            var meta = chart.getDatasetMeta(i);
            if (!meta.hidden) {
                meta.data.forEach(function(element, index) {
                    // Draw the text in white, with the specified font
                    ctx.fillStyle = 'rgb(255, 255, 255)';

                    var fontSize = 18;
                    var fontStyle = 'normal';
                    var fontFamily = 'Arial';

                    ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);

                    // Just naively convert to string for now
                    var dataString = dataset.data[index].toFixed(1) + '%';

                    // Make sure alignment settings are correct
                    ctx.textAlign = 'center';
                    ctx.textBaseline = 'middle';

                    var padding = 5;
                    var position = element.tooltipPosition();
                    ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
                });
            }
        });
    }
});
