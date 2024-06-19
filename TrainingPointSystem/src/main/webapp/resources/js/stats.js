let currentContentId = '';
let charInstance;

function drawChartRevenue(ctx, labels, data, title, type) {
    let colors = [];
    for (let i = 0; i < data.length; i++)
        colors.push(`rgba(${parseInt(Math.random() * 255)}, 
        ${parseInt(Math.random() * 255)}, 
        ${parseInt(Math.random() * 255)}, 0.7)`);

    if (charInstance) {
        charInstance.destroy();
    }
    charInstance = new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [{
                label: title,
                data: data,
                borderWidth: 1,
                backgroundColor: colors
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function showContent(contentId) {
    currentContentId = contentId;
    $('#facultyInputDiv').hide();
    fetchDataAndShow(contentId, null);
}

function showContentWithFaculty(contentId) {
    currentContentId = contentId;
    $('#facultyInputDiv').show();
}

function submitFaculty() {
    const faculty = $('#facultySelect').val();
    fetchDataAndShow(currentContentId, faculty);
}

function fetchDataAndShow(contentId, faculty) {
    const contentTitle = $('#contentTitle');
    const contentThead = $('#contentThead');
    const contentTbody = $('#contentTbody');
    const baseURL = "/TrainingPointSystem/admin"
    let url = '';
    let title = '';
    let headers = '';
    let titleChart;
    let labels = [];
    let dataChart = [];

    if (contentId === 'toanTruongContent') {
        url = `${baseURL}/stats/all`;
        title = 'Toàn trường';
        headers = `
            <tr>
                <th>Số thứ tự</th>
                <th>Tên khoa</th>
                <th>Tổng số học sinh</th>
                <th>Điểm rèn luyện trung bình</th>
            </tr>`;
    } else if (contentId === 'theoKhoaContent') {
        url = `${baseURL}/stats/class`;
        title = 'Theo khoa';
        headers = `
            <tr>
                <th>Số thứ tự</th>
                <th>Tên lớp</th>
                <th>Tổng số học sinh</th>
                <th>Điểm rèn luyện trung bình</th>
            </tr>`;
    } else if (contentId === 'theoThanhTichContent') {
        url = `${baseURL}/stats/rank`;
        title = 'Theo thành tích';
        headers = `
            <tr>
               <td>Loại thành tích</td>
               <td>Giá trị</td>
            </tr>`;
        labels = ['Xuất sắc', 'Giỏi', 'Khá', 'Trung bình', 'Yếu', 'Kém'];
        dataChart = [0, 0, 0, 0, 0, 0];
    }

    contentTitle.html(title);
    contentThead.html(headers);

    $.ajax({
        url: url,
        type: 'GET',
        data: faculty ? {facultyId: faculty} : {},
        success: function (data) {
            let rows = '';
            let index = 1;
            data.forEach(item => {
                if (contentId === 'toanTruongContent' || contentId === 'theoKhoaContent') {
                    let row = '<tr>';
                    if (contentId === 'toanTruongContent') {
                        row += `<td>${index++}</td>`;
                        row += `<td>${item.facultyName}</td>`;
                        row += `<td>${item.totalStudents}</td>`;
                        row += `<td>${item.avgTotalPoints}</td>`;
                        labels.push(item.facultyName);
                        dataChart.push(item.avgTotalPoints);
                    } else if (contentId === 'theoKhoaContent') {
                        row += `<td>${index++}</td>`;
                        row += `<td>${item.name}</td>`;
                        row += `<td>${item.totalStudents}</td>`;
                        row += `<td>${item.avgTotalPoints}</td>`;
                        labels.push(item.name);
                        dataChart.push(item.avgTotalPoints);
                    }
                    row += '</tr>';
                    rows += row;
                } else if (contentId === 'theoThanhTichContent') {
                    rows += `<tr><td>Xuất sắc</td><td>${item.excellent}</td></tr>`;
                    rows += `<tr><td>Giỏi</td><td>${item.good}</td></tr>`;
                    rows += `<tr><td>Khá</td><td>${item.fair}</td></tr>`;
                    rows += `<tr><td>Trung bình</td><td>${item.average}</td></tr>`;
                    rows += `<tr><td>Yếu</td><td>${item.weak}</td></tr>`;
                    rows += `<tr><td>Kém</td><td>${item.poor}</td></tr>`;

                    dataChart[0] += item.excellent;
                    dataChart[1] += item.good;
                    dataChart[2] += item.fair;
                    dataChart[3] += item.average;
                    dataChart[4] += item.weak;
                    dataChart[5] += item.poor;
                }
            });
            contentTbody.html(rows);

            let ctx = document.getElementById("myChart").getContext('2d');
            if (contentId === 'toanTruongContent') {
                titleChart = 'Thống kê trung bình';
                drawChartRevenue(ctx, labels, dataChart, titleChart, 'bar');
            } else if (contentId === 'theoKhoaContent') {
                titleChart = 'Thống kê trung bình theo khoa';
                drawChartRevenue(ctx, labels, dataChart, titleChart, 'bar');
            } else if (contentId === 'theoThanhTichContent') {
                titleChart = 'Thống kê số lượng thành tích';
                drawChartRevenue(ctx, labels, dataChart, titleChart, 'pie');
            }
        },
        error: function (error) {
            console.error('Error fetching data:', error);
            contentTbody.html('<tr><td colspan="9">Error loading data</td></tr>');
        }
    });
}

function extractTableData() {
    // Initialize empty arrays for headers and rows
    let headers = [];
    let rows = [];

    // Get the table element by its ID
    let table = document.getElementById("contentTable");
    let title = "Thống kê điểm rèn luyện " + document.getElementById("contentTitle").innerText;

    // Get the table headers (first row)
    let headerRow = table.rows[0];
    for (let i = 0; i < headerRow.cells.length; i++) {
        headers.push(headerRow.cells[i].innerText);
    }

    // Get the table rows (excluding the header row)
    for (let i = 1; i < table.rows.length; i++) {
        let row = [];
        for (let j = 0; j < table.rows[i].cells.length; j++) {
            row.push(table.rows[i].cells[j].innerText);
        }
        rows.push(row);
    }

    // Construct JSON object
    let jsonData = {
        "title": title,
        "headers": headers,
        "rows": rows
    };

    // Convert object to JSON string
    let jsonString = JSON.stringify(jsonData);

    // Log JSON string (for testing)
    console.log(jsonString);

    // Return JSON string
    return jsonString;
}

function generatePdf() {
    let data = extractTableData();

    $.ajax({
        url: `/TrainingPointSystem/generatePdf`,
        type: "POST",
        contentType: "application/json",
        data: data,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (response) {
            let blob = new Blob([response], {type: 'application/pdf'});
            let url = window.URL.createObjectURL(blob);
            let a = document.createElement('a');
            a.href = url;
            a.download = 'report.pdf';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(a);
            console.log("PDF generated successfully")
        }
    })
}


