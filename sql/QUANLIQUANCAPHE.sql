
CREATE DATABASE QUANLIQUANCAPHE;
GO


USE QUANLIQUANCAPHE;
GO


CREATE TABLE KhuyenMai (
    maKM INT IDENTITY(1,1) PRIMARY KEY,
    tenKM NVARCHAR(100),
    moTa NVARCHAR(200),
    phanTramGiam DECIMAL(5,2)
);


CREATE TABLE ChucVu (
    maCV INT IDENTITY(1,1) PRIMARY KEY,
    tenCV NVARCHAR(100)
);


CREATE TABLE NhanVien (
    maNhanVien VARCHAR(10) PRIMARY KEY,
    tenNhanVien NVARCHAR(100),
    tuoi INT,
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15),
    maCV INT,
    luongNV FLOAT,
    ngayVaoLam DATE,
    gioiTinh NVARCHAR(10),
    FOREIGN KEY (maCV) REFERENCES ChucVu(maCV)
);


CREATE TABLE CaLamViec (
    maCaLamViec VARCHAR(10) PRIMARY KEY,
    thoiGianBatDau DATETIME,
    thoiGianKetThuc DATETIME,
    tienCongCa FLOAT,
    tienDongCa FLOAT,
    heSoLuong FLOAT
);


CREATE TABLE PhanCong (
    maPhanCong VARCHAR(10) PRIMARY KEY,
    ngayPhanCong DATE,
    maNhanVien VARCHAR(10),
    maCaLamViec VARCHAR(10),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    FOREIGN KEY (maCaLamViec) REFERENCES CaLamViec(maCaLamViec)
);


CREATE TABLE PhuongThucThanhToan (
    maPTTT INT IDENTITY(1,1) PRIMARY KEY,
    tenPTTT NVARCHAR(50),
    moTa NVARCHAR(100)
);


CREATE TABLE KhachHang (
    maKhachHang VARCHAR(10) PRIMARY KEY,
    tenKhachHang NVARCHAR(100),
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15)
);


CREATE TABLE NhaCungCap (
    maNCC VARCHAR(10) PRIMARY KEY,
    tenNCC NVARCHAR(100),
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15)
);


CREATE TABLE SanPham (
    maSanPham VARCHAR(10) PRIMARY KEY,
    tenSanPham NVARCHAR(100),
    giaBan FLOAT,
    soLuong INT,
    maNCC VARCHAR(10),
    donViTinh NVARCHAR(20),
    FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC)
);


CREATE TABLE HoaDon (
    maHoaDon VARCHAR(10) PRIMARY KEY,
    maNhanVien VARCHAR(10),
    ngayLap DATETIME,
    tongTien FLOAT,
    maPTTT INT,
    maKhachHang VARCHAR(10),
    maKM INT,
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    FOREIGN KEY (maPTTT) REFERENCES PhuongThucThanhToan(maPTTT),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM)
);


CREATE TABLE ChiTietHoaDon (
    maChiTietHoaDon VARCHAR(10) PRIMARY KEY,
    maHoaDon VARCHAR(10),
    maSanPham VARCHAR(10),
    soLuong INT,
    giaBan FLOAT,
    thanhTien FLOAT,
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maSanPham) REFERENCES SanPham(maSanPham)
);


CREATE TABLE Ban (
    maBan VARCHAR(10) PRIMARY KEY,
    tenBan NVARCHAR(50) NOT NULL,
    sucChua INT NOT NULL,
    viTri NVARCHAR(100),
    trangThai NVARCHAR(50) DEFAULT N'Trống',
    ghiChu NVARCHAR(255)
);


CREATE TABLE ChiTietDatBan (
    maCTDB VARCHAR(10) PRIMARY KEY,            
    maKhachHang VARCHAR(10),                    
    maBan VARCHAR(10) NOT NULL,                 
    maNhanVien VARCHAR(10),                     
    ngayDat DATE NOT NULL,                     
    gioDat TIME NOT NULL,                      
    soNguoi INT NOT NULL,                       
    trangThai NVARCHAR(50) DEFAULT N'Chưa đến', 
    ghiChu NVARCHAR(255) NULL,                  

    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maBan) REFERENCES Ban(maBan),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);



INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, soDienThoai)
VALUES 
('NCC01', N'Công ty Trung Nguyên'	   , N'45 Nguyễn Huệ, TP.HCM'			    , '0905123456'),
('NCC02', N'Vinacafe Biên Hòa'		   , N'KCN Biên Hòa, Đồng Nai'				, '0918123456'),
('NCC03', N'Cty Cung Cấp Cà Phê Nam'   , N'78 Lý Thuờng Kiệt, Quận 10, TP.HCM'	, '0911122233'),
('NC004', N'Nguyên Liệu Trung Nguyên'  , N'15 Trường Sơn, Tân Bình, TP.HCM'     , '0933444555'),
('NC005', N'Cung ứng Ðồ Uống Ðại Dương', N'99 Ðinh Bộ Lĩnh, Bình Thạnh, TP.HCM' , '0945566778');
GO

INSERT INTO KhuyenMai (tenKM, moTa, phanTramGiam)
VALUES 
(N'Giảm 10%', N'Giảm giá cho khách hàng thân thiết', 10.00),
(N'Giảm 15%', N'Khuyến mãi dịp lễ', 15.00),
(N'Giảm 20%', N'Giảm cho hóa đơn trên 500k', 20.00);
GO

INSERT INTO ChucVu (tenCV)
VALUES 
(N'Quản lý'),
(N'Thu ngân'),
(N'Thu ngân'),
(N'Pha chế');

INSERT INTO NhanVien (maNhanVien, tenNhanVien, tuoi, diaChi, soDienThoai, maCV, luongNV, ngayVaoLam, gioiTinh)
VALUES 
('NV01', N'Nguyễn Văn A', 28, N'123 Lê Lợi, Q1', '0909123456', 1, 12000000, '2023-05-12', N'Nam'),
('NV02', N'Trần Thị B', 25, N'45 Hai Bà Trưng, Q1', '0909555123', 2, 8000000, '2023-07-01', N'Nữ'),
('NV03', N'Lê Văn C', 22, N'78 Nguyễn Trãi, Q5', '0912345678', 3, 7000000, '2023-06-20', N'Nam');

INSERT INTO CaLamViec (maCaLamViec, thoiGianBatDau, thoiGianKetThuc, tienCongCa, tienDongCa, heSoLuong)
VALUES
('CA1', '2024-01-01 06:00', '2024-01-01 14:00', 200000, 0, 1.0),
('CA2', '2024-01-01 14:00', '2024-01-01 22:00', 220000, 0, 1.2);

INSERT INTO PhanCong (maPhanCong, ngayPhanCong, maNhanVien, maCaLamViec)
VALUES
('PC01', '2024-10-01', 'NV01', 'CA1'),
('PC02', '2024-10-01', 'NV02', 'CA2'),
('PC03', '2024-10-02', 'NV03', 'CA1');

INSERT INTO PhuongThucThanhToan (tenPTTT, moTa)
VALUES 
(N'Tiền mặt', N'Thanh toán bằng tiền mặt'),
(N'Chuyển khoản', N'Qua ngân hàng'),
(N'Ví điện tử', N'Qua Momo hoặc ZaloPay');

INSERT INTO KhachHang (maKhachHang, tenKhachHang, diaChi, soDienThoai)
VALUES 
('KH01', N'Lê Hoàng Minh', N'12 Trần Hưng Đạo, Q1', '0908000111'),
('KH02', N'Phạm Thu Hà', N'25 Nguyễn Văn Cừ, Q5', '0908666222'),
('KH03', N'Võ Thanh Tùng', N'78 Võ Văn Kiệt, Q6', '0908333444');

UPDATE NhaCungCap SET maNCC = 'NCC04' WHERE maNCC = 'NC004';
UPDATE NhaCungCap SET maNCC = 'NCC05' WHERE maNCC = 'NC005';


INSERT INTO SanPham (maSanPham, tenSanPham, giaBan, soLuong, maNCC, donViTinh)
VALUES
-- Nhóm cà phê (NCC01)
('SP01', N'Cà phê đen đá', 25000, 100, 'NCC01', N'Ly'),
('SP02', N'Cà phê sữa đá', 30000, 120, 'NCC01', N'Ly'),
('SP03', N'Bạc xỉu', 32000, 100, 'NCC01', N'Ly'),
('SP04', N'Cà phê Espresso', 40000, 80, 'NCC01', N'Ly'),
('SP05', N'Cà phê Cappuccino', 45000, 80, 'NCC01', N'Ly'),
('SP06', N'Cà phê Latte', 45000, 80, 'NCC01', N'Ly'),

-- Nhóm sữa & nước uống (NCC02)
('SP07', N'Sữa tươi Vinamilk', 20000, 200, 'NCC02', N'Chai'),
('SP08', N'Sữa chua uống', 18000, 150, 'NCC02', N'Chai'),
('SP09', N'Sữa đặc có đường', 25000, 100, 'NCC02', N'Hộp'),
('SP10', N'Sữa đậu nành', 22000, 150, 'NCC02', N'Ly'),

-- Nhóm nước đóng chai (NCC03)
('SP11', N'Nước suối Lavie', 15000, 200, 'NCC03', N'Chai'),
('SP12', N'Trà chanh Nestlé', 25000, 150, 'NCC03', N'Chai'),
('SP13', N'Trà đào', 30000, 100, 'NCC03', N'Ly'),
('SP14', N'Trà sữa trân châu', 35000, 150, 'NCC03', N'Ly'),
('SP15', N'Trà gừng mật ong', 28000, 100, 'NCC03', N'Ly'),

-- Nhóm bánh ngọt (NCC04)
('SP16', N'Bánh su kem', 20000, 100, 'NCC04', N'Cái'),
('SP17', N'Bánh tiramisu', 40000, 60, 'NCC04', N'Miếng'),
('SP18', N'Bánh mousse socola', 45000, 60, 'NCC04', N'Miếng'),
('SP19', N'Bánh cheese cake', 50000, 50, 'NCC04', N'Miếng'),
('SP20', N'Bánh cookie bơ', 15000, 120, 'NCC04', N'Cái'),

-- Nhóm topping (NCC05)
('SP21', N'Trân châu đen', 5000, 300, 'NCC05', N'Phần'),
('SP22', N'Trân châu trắng', 5000, 250, 'NCC05', N'Phần'),
('SP23', N'Pudding trứng', 7000, 200, 'NCC05', N'Phần'),
('SP24', N'Thạch trái cây', 6000, 200, 'NCC05', N'Phần'),
('SP25', N'Kem tươi', 10000, 150, 'NCC05', N'Phần'),

-- Đặc biệt (mix)
('SP26', N'Sinh tố xoài', 35000, 100, 'NCC03', N'Ly'),
('SP27', N'Sinh tố dâu', 35000, 100, 'NCC03', N'Ly'),
('SP28', N'Sinh tố bơ', 38000, 100, 'NCC03', N'Ly'),
('SP29', N'Nước cam ép', 30000, 120, 'NCC03', N'Ly'),
('SP30', N'Chocolate đá xay', 45000, 100, 'NCC01', N'Ly');

INSERT INTO Ban (maBan, tenBan, sucChua, viTri, trangThai, ghiChu)
VALUES 
('B01', N'Bàn 1', 4, N'Tầng 1 - Gần cửa', N'Trống', N''),
('B02', N'Bàn 2', 6, N'Tầng 1 - Giữa quán', N'Trống', N''),
('B03', N'Bàn 3', 2, N'Tầng 2 - View phố', N'Trống', N'');

INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayLap, tongTien, maPTTT, maKhachHang, maKM)
VALUES 
('HD01', 'NV02', '2024-10-10 10:00', 65000, 1, 'KH01', 1),
('HD02', 'NV02', '2024-10-11 15:00', 90000, 2, 'KH02', 2);

INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maHoaDon, maSanPham, soLuong, giaBan, thanhTien)
VALUES 
('CTHD01', 'HD01', 'SP01', 1, 25000, 25000),
('CTHD02', 'HD01', 'SP02', 1, 30000, 30000),
('CTHD03', 'HD02', 'SP03', 2, 35000, 70000);

INSERT INTO ChiTietDatBan (maCTDB, maKhachHang, maBan, maNhanVien, ngayDat, gioDat, soNguoi, trangThai, ghiChu)
VALUES
('DB01', 'KH01', 'B01', 'NV03', '2024-10-15', '08:30', 2, N'Đã đặt', N'Khách đến sớm'),
('DB02', 'KH02', 'B02', 'NV01', '2024-10-16', '18:00', 4, N'Chưa đến', N'Bàn ngoài trời');

ALTER TABLE HoaDon
ADD trangThaiThanhToan NVARCHAR(50);





