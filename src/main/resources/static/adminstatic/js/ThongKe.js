document.addEventListener("DOMContentLoaded", function() {
    const studentsData = [
        { name: "Nguyễn Văn A", studentID: "SV001", examType: "Luyện tập", accessType: "Luôn mở", date: "2024-02-01", participation: 10, averageScore: 9 },
        { name: "Trần Thị B", studentID: "SV002", examType: "Giữa kỳ", accessType: "Theo lịch thi", date: "2024-02-15", participation: 8, averageScore: 7 },
        { name: "Phạm Văn C", studentID: "SV003", examType: "Cuối kỳ", accessType: "Luôn mở", date: "2024-03-05", participation: 12, averageScore: 9 },
        { name: "Lê Thị D", studentID: "SV004", examType: "Luyện tập", accessType: "Luôn mở", date: "2024-02-10", participation: 15, averageScore: 8 },
        { name: "Hoàng Văn E", studentID: "SV005", examType: "Cuối kỳ", accessType: "Theo lịch thi", date: "2024-03-20", participation: 9, averageScore: 7 },
        { name: "Mai Thị F", studentID: "SV006", examType: "Luyện tập", accessType: "Luôn mở", date: "2024-02-01", participation: 11, averageScore: 8 },
        { name: "Đặng Văn G", studentID: "SV007", examType: "Giữa kỳ", accessType: "Theo lịch thi", date: "2024-02-15", participation: 7, averageScore: 6 },
        { name: "Vũ Thị H", studentID: "SV008", examType: "Cuối kỳ", accessType: "Luôn mở", date: "2024-03-05", participation: 13, averageScore: 9 },
        { name: "Hồ Văn I", studentID: "SV009", examType: "Luyện tập", accessType: "Theo lịch thi", date: "2024-02-10", participation: 14, averageScore: 8 },
        { name: "Trần Văn K", studentID: "SV010", examType: "Cuối kỳ", accessType: "Theo lịch thi", date: "2024-03-20", participation: 10, averageScore: 9 }
    ];

    const applyFiltersButton = document.getElementById("applyFilters");
    const exportExcelButton = document.getElementById("exportExcel");

    applyFiltersButton.addEventListener("click", function() {
        applyFilters();
    });

    exportExcelButton.addEventListener("click", function() {
        exportExcel();
    });

    // Hiển thị danh sách sinh viên khi trang được tải
    updateStatistics("all", "all", "");

    function applyFilters() {
        const examType = document.getElementById("examType").value;
        const accessType = document.getElementById("accessType").value;
        const date = document.getElementById("date").value;

        // Thực hiện logic áp dụng bộ lọc ở đây
        // Sau khi lọc dữ liệu, cập nhật bảng thống kê và biểu đồ
        updateStatistics(examType, accessType, date);
        drawLineChart();
    }

    function updateStatistics(examType, accessType, date) {
        const statsTableBody = document.getElementById("statsBody");
    
        // Xóa nội dung cũ trong bảng
        statsTableBody.innerHTML = "";
    
        // Lọc dữ liệu sinh viên dựa trên yêu cầu của bộ lọc
        let filteredStudents = studentsData;
    
        if (examType !== 'all') {
            filteredStudents = filteredStudents.filter(student => student.examType === examType);
        }
    
        if (accessType !== 'all') {
            filteredStudents = filteredStudents.filter(student => student.accessType === accessType);
        }
    
        if (date !== '') {
            filteredStudents = filteredStudents.filter(student => student.date === date);
        }
    
        // Nếu không có bộ lọc nào được áp dụng, hiển thị tất cả sinh viên
        if (examType === 'all' && accessType === 'all' && date === '') {
            filteredStudents = studentsData;
        }
    
        // Biến đếm cho số thứ tự sinh viên
        let studentCount = 1;
    
        // Thêm dữ liệu mới vào bảng
        filteredStudents.forEach(function(student) {
            const row = document.createElement("tr");
    
            row.innerHTML = `
                <td>${studentCount}</td>
                <td>${student.name}</td>
                <td>${student.studentID}</td>
                <td>${student.examType}</td>
                <td>${student.accessType}</td>
                <td>${student.date}</td>
                <td>${student.participation}</td>
                <td>${calculateCompletionRate(student.averageScore)}</td>
                <td>${student.averageScore}</td>
            `;
            statsTableBody.appendChild(row);
    
            // Tăng biến đếm
            studentCount++;
        });
    }    

    function calculateCompletionRate(averageScore) {
        const maxScore = 10.0;
        const completionRate = (averageScore / maxScore) * 100;
        return completionRate.toFixed(2) + "%";
    }

    function drawHistogram() {
        const canvas = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');

        // Tạo mảng chứa tần suất của từng điểm số
        const frequency = Array.from({ length: 11 }, () => 0);

        // Tính toán tần suất của từng điểm số
        studentsData.forEach(student => {
            const score = Math.round(student.averageScore); // Làm tròn điểm trung bình thành số nguyên
            frequency[score]++;
        });

        // Vẽ biểu đồ histogram
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Array.from({ length: 11 }, (_, i) => i), // Nhãn trục x từ 0 đến 10
                datasets: [{
                    label: 'Tần suất',
                    data: frequency,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)', // Màu nền của cột
                    borderColor: 'rgba(54, 162, 235, 1)', // Màu viền của cột
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Tần suất'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Điểm số'
                        }
                    }
                }
            }
        });
    }   
    
    //biểu đồ đường
    function drawLineChart() {
        const canvas = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');
    
        // Tạo mảng chứa tần suất của từng điểm số
        const frequency = Array.from({ length: 11 }, () => 0);
    
        // Tính toán tần suất của từng điểm số
        studentsData.forEach(student => {
            const score = Math.round(student.averageScore); // Làm tròn điểm trung bình thành số nguyên
            frequency[score]++;
        });
    
        // Vẽ biểu đồ đường
        myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: Array.from({ length: 11 }, (_, i) => i), // Nhãn trục x từ 0 đến 10
                datasets: [{
                    label: 'Tần suất',
                    data: frequency,
                    fill: false,
                    borderColor: 'rgba(54, 162, 235, 1)', // Màu của đường
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Tần suất'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Điểm số'
                        }
                    }
                }
            }
        });
    }
    
    
    // Xuất danh sách thành tệp Excel
    function exportExcel() {
        const table = document.getElementById("statistics");
        const wb = XLSX.utils.table_to_book(table, { sheet: "Sheet JS" });
        XLSX.writeFile(wb, "statistics.xlsx");
    }
});
