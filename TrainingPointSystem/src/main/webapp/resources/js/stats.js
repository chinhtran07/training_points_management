let currentContentId = '';

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
    if (faculty) {
        fetchDataAndShow(currentContentId, faculty);
    } else {
        alert('Vui lòng nhập tên khoa');
    }
}

function fetchDataAndShow(contentId, faculty) {
    const contentTitle = $('#contentTitle');
    const contentThead = $('#contentThead');
    const contentTbody = $('#contentTbody');
    const baseURL = "/TrainingPointSystem/admin"
    let url = '';
    let title = '';
    let headers = '';

    if (contentId === 'toanTruongContent') {
        url = `${baseURL}/stats/all`;
        title = 'Toàn trường';
        headers = `
            <tr>
                <th>Faculty Name</th>
                <th>Excellent</th>
                <th>Good</th>
                <th>Fair</th>
                <th>Average</th>
                <th>Weak</th>
                <th>Poor</th>
                <th>Total Students</th>
                <th>Avg Total Points</th>
            </tr>`;
    } else if (contentId === 'theoKhoaContent') {
        url = `${baseURL}/stats/class`;
        title = 'Theo khoa';
        headers = `
            <tr>
                <th>Name</th>
                <th>Total Students</th>
                <th>Avg Total Points</th>
            </tr>`;
    } else if (contentId === 'theoThanhTichContent') {
        url = `${baseURL}/stats/rank`;
        title = 'Theo thành tích';
        headers = `
            <tr>
                <th>Excellent</th>
                <th>Good</th>
                <th>Fair</th>
                <th>Average</th>
                <th>Weak</th>
                <th>Poor</th>
            </tr>`;
    }

    contentTitle.html(title);
    contentThead.html(headers);

    $.ajax({
        url: url,
        type: 'GET',
        data: faculty ? { faculty: faculty } : {},
        success: function(data) {
            let rows = '';
            data.forEach(item => {
                let row = '<tr>';
                if (contentId === 'toanTruongContent') {
                    row += `<td>${item.facultyName}</td>`;
                    row += `<td>${item.excellent}</td>`;
                    row += `<td>${item.good}</td>`;
                    row += `<td>${item.fair}</td>`;
                    row += `<td>${item.average}</td>`;
                    row += `<td>${item.weak}</td>`;
                    row += `<td>${item.poor}</td>`;
                    row += `<td>${item.totalStudents}</td>`;
                    row += `<td>${item.avgTotalPoints}</td>`;
                } else if (contentId === 'theoKhoaContent') {
                    row += `<td>${item.name}</td>`;
                    row += `<td>${item.totalStudents}</td>`;
                    row += `<td>${item.avgTotalPoints}</td>`;
                } else if (contentId === 'theoThanhTichContent') {
                    row += `<td>${item.excellent}</td>`;
                    row += `<td>${item.good}</td>`;
                    row += `<td>${item.fair}</td>`;
                    row += `<td>${item.average}</td>`;
                    row += `<td>${item.weak}</td>`;
                    row += `<td>${item.poor}</td>`;
                }
                row += '</tr>';
                rows += row;
            });
            contentTbody.html(rows);
        },
        error: function(error) {
            console.error('Error fetching data:', error);
            contentTbody.html('<tr><td colspan="9">Error loading data</td></tr>');
        }
    });
}
