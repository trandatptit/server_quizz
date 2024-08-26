document.addEventListener('DOMContentLoaded', function() {
    var statsBody = document.getElementById('statsBody');

    // Dữ liệu của 20 sinh viên
    var studentsData = [];

    for (var i = 1; i <= 20; i++) {
        var student = {
            stt: i,
            username: 'student' + i,
            password: 'password' + i,
            email: 'student' + i + '@example.com',
            fullName: 'Họ và tên ' + i,
            studentID: 'SV00' + (i < 10 ? '0' + i : i),
            createdDate: '2024-03-09'
        };
        studentsData.push(student);
    }


    // Thêm dữ liệu vào bảng và thêm chức năng sửa, xóa
    studentsData.forEach(function(student) {
        var row = document.createElement('tr');
        row.innerHTML = '<td>' + student.stt + '</td>' +
                        '<td>' + student.username + '</td>' +
                        '<td>' + student.password + '</td>' +
                        '<td>' + student.email + '</td>' +
                        '<td>' + student.fullName + '</td>' +
                        '<td>' + student.studentID + '</td>' +
                        '<td>' + student.createdDate + '</td>' +
                        '<td><button onclick="openModal(' + student.stt + ')">Sửa</button><button onclick="deleteStudent(' + student.stt + ')">Xóa</button></td>';
        statsBody.appendChild(row);
    });
});

// Mở modal và hiển thị thông tin cũ của sinh viên
function openModal(stt) {
    var row = document.querySelector('#statsBody tr:nth-child(' + stt + ')');
    document.getElementById('oldUsername').value = row.cells[1].textContent;
    document.getElementById('oldPassword').value = row.cells[2].textContent;
    document.getElementById('oldEmail').value = row.cells[3].textContent;
    document.getElementById('oldFullName').value = row.cells[4].textContent;
    document.getElementById('oldStudentID').value = row.cells[5].textContent;
    document.getElementById('oldCreatedDate').value = row.cells[6].textContent;
    document.getElementById('editModal').style.display = 'block';
}

// Đóng modal
function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

// Lưu thay đổi
function saveChanges() {
    
    // Thêm logic lưu thay đổi ở đây
    closeModal();
}

function deleteStudent(stt) {
    var confirmation = confirm('Bạn có chắc muốn xóa sinh viên có STT: ' + stt + '?');
    if (confirmation) {
        var row = document.querySelector('#statsBody tr:nth-child(' + stt + ')');
        if (row) {
            row.remove();
            console.log('Xóa thành công sinh viên có STT: ' + stt);

            // Cập nhật lại STT của các sinh viên còn lại
            var rows = document.querySelectorAll('#statsBody tr');
            rows.forEach(function(row, index) {
                var currentSTT = parseInt(row.cells[0].textContent);
                if (currentSTT > stt) {
                    row.cells[0].textContent = currentSTT - 1;
                }
            });
        } else {
            console.error('Không tìm thấy sinh viên có STT: ' + stt);
        }
    }
}
