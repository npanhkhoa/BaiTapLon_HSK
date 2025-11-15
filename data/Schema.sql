USE [master]
GO
-- Create Database
-- Tạo Database
CREATE DATABASE [QuanLiCoffee];
GO

USE [QuanLiCoffee];
GO

-- Cấu hình cơ bản
ALTER DATABASE [QuanLiCoffee] SET COMPATIBILITY_LEVEL = 160;
ALTER DATABASE [QuanLiCoffee] SET AUTO_CLOSE ON;
ALTER DATABASE [QuanLiCoffee] SET RECOVERY SIMPLE;
GO

-- Bảng TaiKhoan
CREATE TABLE [dbo].[TaiKhoan] (
    [tenDangNhap] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [matKhau] NVARCHAR(10) NOT NULL,
    [quyen] BIT NOT NULL
    );
GO

-- Bảng NhanVien
CREATE TABLE [dbo].[NhanVien] (
    [maNhanVien] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenNhanVien] NVARCHAR(100) NOT NULL,
    [tuoi] INT NOT NULL,
    [diaChi] NVARCHAR(200) NOT NULL,
    [soDienThoai] NVARCHAR(15) NOT NULL,
    [tenDangNhap] NVARCHAR(10) NOT NULL,
    FOREIGN KEY ([tenDangNhap]) REFERENCES [dbo].[TaiKhoan]([tenDangNhap])
    );
GO

-- Bảng CaLamViec
CREATE TABLE [dbo].[CaLamViec] (
    [maCaLamViec] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenDangNhap] NVARCHAR(10) NOT NULL,
    [thoiGianBatDau] DATETIME NOT NULL,
    [thoiGianKetThuc] DATETIME NOT NULL,
    [tienMoCa] DECIMAL(10,2) NOT NULL,
    [tienDongCa] DECIMAL(10,2) NOT NULL,
    FOREIGN KEY ([tenDangNhap]) REFERENCES [dbo].[TaiKhoan]([tenDangNhap])
    );
GO

-- Bảng LoaiSanPham
CREATE TABLE [dbo].[LoaiSanPham] (
    [maLoaiSanPham] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenLoaiSanPham] NVARCHAR(100) NOT NULL
    );
GO

-- Bảng NguyenLieu
CREATE TABLE [dbo].[NguyenLieu] (
    [maNguyenLieu] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenNguyenLieu] NVARCHAR(100) NOT NULL,
    [donViTinh] NVARCHAR(20) NOT NULL,
    [giaNhap] DECIMAL(10,2) NOT NULL,
    [ngayNhap] DATE  DEFAULT ('2025-01-01'),
    [ngayHetHan] DATE DEFAULT ('2025-01-01')
    );
GO

-- Bảng SanPham
CREATE TABLE [dbo].[SanPham] (
    [maSanPham] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenSanPham] NVARCHAR(100) NOT NULL,
    [giaBan] DECIMAL(10,2) NOT NULL,
    [soLuong] INT NOT NULL,
    [maLoaiSanPham] NVARCHAR(10) NOT NULL,
    [hinhAnh] NVARCHAR(50),
    [maNguyenLieu] NVARCHAR(10),
    FOREIGN KEY ([maLoaiSanPham]) REFERENCES [dbo].[LoaiSanPham]([maLoaiSanPham]),
    FOREIGN KEY ([maNguyenLieu]) REFERENCES [dbo].[NguyenLieu]([maNguyenLieu])
    );
GO

-- Bảng HoaDon
CREATE TABLE [dbo].[HoaDon] (
    [maHoaDon] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [maNhanVien] NVARCHAR(10) NOT NULL,
    [ngayLap] DATETIME NOT NULL,
    [tongTien] DECIMAL(10,2) NOT NULL DEFAULT 0,
    FOREIGN KEY ([maNhanVien]) REFERENCES [dbo].[NhanVien]([maNhanVien])
    );
GO

-- Bảng ChiTietHoaDon
CREATE TABLE [dbo].[ChiTietHoaDon] (
    [maChiTietHoaDon] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [maHoaDon] NVARCHAR(10) NOT NULL,
    [maSanPham] NVARCHAR(10) NOT NULL,
    [soLuong] INT NOT NULL,
    [giaBan] DECIMAL(10,2) NOT NULL,
    [thanhTien] DECIMAL(10,2) NOT NULL,
    FOREIGN KEY ([maHoaDon]) REFERENCES [dbo].[HoaDon]([maHoaDon]),
    FOREIGN KEY ([maSanPham]) REFERENCES [dbo].[SanPham]([maSanPham])
    );
GO

-- Bảng KhoNguyenLieu
CREATE TABLE [dbo].[KhoNguyenLieu] (
    [maKho] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenKho] NVARCHAR(100) NOT NULL,
    [diaChi] NVARCHAR(200) NOT NULL
    );
GO

-- Bảng ChiTietKhoNguyenLieu
CREATE TABLE [dbo].[ChiTietKhoNguyenLieu] (
    [maKho] NVARCHAR(10) NOT NULL,
    [maNguyenLieu] NVARCHAR(10) NOT NULL,
    [soLuong] INT NOT NULL,
    PRIMARY KEY ([maKho], [maNguyenLieu]),
    FOREIGN KEY ([maKho]) REFERENCES [dbo].[KhoNguyenLieu]([maKho]),
    FOREIGN KEY ([maNguyenLieu]) REFERENCES [dbo].[NguyenLieu]([maNguyenLieu])
    );
GO

-- Bảng NhaCungCap
CREATE TABLE [dbo].[NhaCungCap] (
    [maNhaCungCap] NVARCHAR(10) NOT NULL PRIMARY KEY,
    [tenNhaCungCap] NVARCHAR(100) NOT NULL,
    [diaChi] NVARCHAR(200) NOT NULL,
    [soDienThoai] NVARCHAR(15) NOT NULL
    );
GO

-- Bảng ChiTietNhaCungCap
CREATE TABLE [dbo].[ChiTietNhaCungCap] (
    [maNhaCungCap] NVARCHAR(10) NOT NULL,
    [maNguyenLieu] NVARCHAR(10) NOT NULL,
    PRIMARY KEY ([maNhaCungCap], [maNguyenLieu]),
    FOREIGN KEY ([maNhaCungCap]) REFERENCES [dbo].[NhaCungCap]([maNhaCungCap]),
    FOREIGN KEY ([maNguyenLieu]) REFERENCES [dbo].[NguyenLieu]([maNguyenLieu])
    );
GO

INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP01', N'Coffee')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP02', N'Nước Ngọt')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP03', N'Sinh Tố')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP04', N'Trà')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP05', N'Thuốc Lá')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP06', N'Bánh Ngọt')
INSERT [dbo].[LoaiSanPham] ([maLoaiSanPham], [tenLoaiSanPham]) VALUES (N'LSP07', N'Đồ Ăn Vặt')
GO

INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [quyen]) VALUES (N'admin', N'admin', 1)
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [quyen]) VALUES (N'khoa', N'khoa123', 0)
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [quyen]) VALUES (N'hao', N'hao123', 0)
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [quyen]) VALUES (N'huyen', N'huyen123', 0)
GO


INSERT [dbo].[NhanVien] ([maNhanVien], [tenNhanVien], [tuoi], [diaChi], [soDienThoai], [tenDangNhap]) VALUES (N'NV01', N'Khoa', 25, N'Hồ Chí Minh', N'0123456789', N'khoa')
INSERT [dbo].[NhanVien] ([maNhanVien], [tenNhanVien], [tuoi], [diaChi], [soDienThoai], [tenDangNhap]) VALUES (N'NV02', N'Hào', 22, N'TP.Ho Chi Minh', N'0987654321', N'hao')
INSERT [dbo].[NhanVien] ([maNhanVien], [tenNhanVien], [tuoi], [diaChi], [soDienThoai], [tenDangNhap]) VALUES (N'NV03', N'Huyền', 28, N'TP.Ho Chi Minh', N'0912345678', N'huyen')
GO


INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP01', N'Coffee Đen', CAST(25000.00 AS Decimal(10, 2)), 100, N'LSP01', N'Coffee Đen.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP02', N'Coffee Sữa', CAST(28000.00 AS Decimal(10, 2)), 120, N'LSP01', N'Coffee sữa.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP03', N'Latte', CAST(32000.00 AS Decimal(10, 2)), 80, N'LSP01', N'Latte.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP04', N'Cappuccino', CAST(35000.00 AS Decimal(10, 2)), 90, N'LSP01', N'Cappuccino.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP05', N'Coca Cola', CAST(15000.00 AS Decimal(10, 2)), 200, N'LSP02', N'Coca Cola.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP06', N'Sprite', CAST(15000.00 AS Decimal(10, 2)), 180, N'LSP02', N'Sprite.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP07', N'Pepsi', CAST(15000.00 AS Decimal(10, 2)), 160, N'LSP02', N'Pepsi.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP08', N'7Up', CAST(15000.00 AS Decimal(10, 2)), 170, N'LSP02', N'7 up.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP09', N'Sinh Tố Bơ', CAST(30000.00 AS Decimal(10, 2)), 70, N'LSP03', N'Sinh tố bơ.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP10', N'Sinh Tố Dâu', CAST(30000.00 AS Decimal(10, 2)), 60, N'LSP03', N'Sinh tố dâu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP11', N'Sinh Tố Xoài', CAST(30000.00 AS Decimal(10, 2)), 65, N'LSP03', N'Sinh tố xoài.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP12', N'Sinh Tố Mãng Cầu', CAST(32000.00 AS Decimal(10, 2)), 55, N'LSP03', N'Sinh tố  mãng cầu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP13', N'Trà Đào', CAST(25000.00 AS Decimal(10, 2)), 90, N'LSP04', N'Trà đào.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP14', N'Trà Vải', CAST(25000.00 AS Decimal(10, 2)), 85, N'LSP04', N'Trà vải.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP15', N'Trà Sữa Trân Châu', CAST(28000.00 AS Decimal(10, 2)), 95, N'LSP04', N'Trà sữa trân châu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP16', N'Trà Gừng', CAST(22000.00 AS Decimal(10, 2)), 75, N'LSP04', N'Trà gừng.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP17', N'Thuốc Lá Jet', CAST(25000.00 AS Decimal(10, 2)), 50, N'LSP05', N'Jet.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP18', N'Thuốc Lá Hero', CAST(22000.00 AS Decimal(10, 2)), 40, N'LSP05', N'Hero.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP19', N'Thuốc Lá Craven A', CAST(27000.00 AS Decimal(10, 2)), 45, N'LSP05', N'Craven A.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP20', N'Thuốc Lá Esse', CAST(30000.00 AS Decimal(10, 2)), 35, N'LSP05', N'Esse.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP21', N'Americano', CAST(27000.00 AS Decimal(10, 2)), 85, N'LSP01', N'Americano.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP22', N'Mocha', CAST(34000.00 AS Decimal(10, 2)), 75, N'LSP01', N'mocha.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP23', N'Macchiato', CAST(33000.00 AS Decimal(10, 2)), 65, N'LSP01', N'macchiato.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP24', N'Coffee Sữa Đá', CAST(29000.00 AS Decimal(10, 2)), 110, N'LSP01', N'Coffee Sữa Đá.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP25', N'Mirinda Cam', CAST(15000.00 AS Decimal(10, 2)), 190, N'LSP02', N'mỉinda cam.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP26', N'Fanta Nho', CAST(15000.00 AS Decimal(10, 2)), 175, N'LSP02', N'fanta nho.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP27', N'Sarsi', CAST(16000.00 AS Decimal(10, 2)), 140, N'LSP02', N'sarsi.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP28', N'Lipton Ice Tea', CAST(17000.00 AS Decimal(10, 2)), 130, N'LSP02', N'lipton.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP29', N'Sinh Tố Dưa Hấu', CAST(28000.00 AS Decimal(10, 2)), 60, N'LSP03', N'sinh tố dưa hấu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP30', N'Sinh Tố Mít', CAST(31000.00 AS Decimal(10, 2)), 55, N'LSP03', N'sinh tố mít.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP31', N'Sinh Tố Chuối', CAST(27000.00 AS Decimal(10, 2)), 70, N'LSP03', N'sinh tố chuối.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP32', N'Sinh Tố Kiwi', CAST(32000.00 AS Decimal(10, 2)), 50, N'LSP03', N'sinh tố kiwi.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP33', N'Trà Chanh', CAST(22000.00 AS Decimal(10, 2)), 100, N'LSP04', N'trà chanh.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP34', N'Trà Sữa Matcha', CAST(29000.00 AS Decimal(10, 2)), 90, N'LSP04', N'trà sữa matcha.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP35', N'Trà Sữa Socola', CAST(30000.00 AS Decimal(10, 2)), 85, N'LSP04', N'trà sữa socola.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP36', N'Trà Tắc Mật Ong', CAST(23000.00 AS Decimal(10, 2)), 80, N'LSP04', N'trà tắc mật ong.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP37', N'Thuốc Lá Marlboro', CAST(35000.00 AS Decimal(10, 2)), 30, N'LSP05', N'marboro.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP38', N'Thuốc Lá 555', CAST(36000.00 AS Decimal(10, 2)), 25, N'LSP05', N'555.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP39', N'Thuốc Lá Zest', CAST(24000.00 AS Decimal(10, 2)), 45, N'LSP05', N'zest.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP40', N'Thuốc Lá Vinataba', CAST(26000.00 AS Decimal(10, 2)), 50, N'LSP05', N'vinataba.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP41', N'Coffee Cold Brew', CAST(33000.00 AS Decimal(10, 2)), 70, N'LSP01', N'Coffee Cold Brew.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP42', N'Coffee Hazelnut', CAST(34000.00 AS Decimal(10, 2)), 65, N'LSP01', N'Coffee Hazelnut.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP43', N'Coffee Caramel', CAST(35000.00 AS Decimal(10, 2)), 75, N'LSP01', N'Coffee Caramel.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP44', N'Nước Cam C2', CAST(12000.00 AS Decimal(10, 2)), 200, N'LSP02', N'Nước Cam C2.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP45', N'Number One', CAST(14000.00 AS Decimal(10, 2)), 210, N'LSP02', N'Number One.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP46', N'Sting Dâu', CAST(15000.00 AS Decimal(10, 2)), 190, N'LSP02', N'Sting Dâu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP47', N'Sinh Tố Dứa', CAST(29000.00 AS Decimal(10, 2)), 60, N'LSP03', N'Sinh Tố Dứa.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP48', N'Sinh Tố Mãng Cầu Xiêm', CAST(31000.00 AS Decimal(10, 2)), 50, N'LSP03', N'Sinh Tố Mãng Cầu Xiêm.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP49', N'Sinh Tố Táo', CAST(28000.00 AS Decimal(10, 2)), 55, N'LSP03', N'Sinh Tố Táo.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP50', N'Trà Sữa Hồng Trà', CAST(27000.00 AS Decimal(10, 2)), 80, N'LSP04', N'Trà Sữa Hồng Trà.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP51', N'Trà Sữa Oolong', CAST(29000.00 AS Decimal(10, 2)), 85, N'LSP04', N'Trà Sữa Oolong.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP52', N'Trà Hoa Cúc', CAST(24000.00 AS Decimal(10, 2)), 90, N'LSP04', N'Trà Hoa Cúc.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP53', N'Thuốc Lá Dunhill', CAST(37000.00 AS Decimal(10, 2)), 20, N'LSP05', N'Thuốc Lá Dunhill.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP54', N'Thuốc Lá Kent', CAST(38000.00 AS Decimal(10, 2)), 15, N'LSP05', N'Thuốc Lá Kent.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP55', N'Bánh Su Kem', CAST(18000.00 AS Decimal(10, 2)), 120, N'LSP06', N'Bánh Su Kem.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP56', N'Bánh Mì Bơ Tỏi', CAST(22000.00 AS Decimal(10, 2)), 100, N'LSP06', N'Bánh Mì Bơ Tỏi.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP57', N'Bánh Flan', CAST(20000.00 AS Decimal(10, 2)), 110, N'LSP06', N'Bánh Flan.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP58', N'Bánh Tiramisu', CAST(35000.00 AS Decimal(10, 2)), 70, N'LSP06', N'Bánh Tiramisu.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP59', N'Bánh Donut Socola', CAST(25000.00 AS Decimal(10, 2)), 90, N'LSP06', N'Bánh Donut Socola.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP60', N'Khoai Tây Chiên', CAST(30000.00 AS Decimal(10, 2)), 130, N'LSP07', N'Khoai Tây Chiên.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP61', N'Xúc Xích Đức', CAST(28000.00 AS Decimal(10, 2)), 100, N'LSP07', N'Xúc Xích Đức.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP62', N'Nem Chua Rán', CAST(25000.00 AS Decimal(10, 2)), 95, N'LSP07', N'Nem Chua Rán.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP63', N'Cá Viên Chiên', CAST(27000.00 AS Decimal(10, 2)), 115, N'LSP07', N'Cá Viên Chiên.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP64', N'Bắp Xào', CAST(22000.00 AS Decimal(10, 2)), 105, N'LSP07', N'Bắp Xào.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP65', N'Phô Mai Que', CAST(26000.00 AS Decimal(10, 2)), 100, N'LSP07', N'Phô Mai Que.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP66', N'Coffee Vanilla', CAST(34000.00 AS Decimal(10, 2)), 60, N'LSP01', N'Coffee Vanilla.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP67', N'Trà Sữa Thái Xanh', CAST(29000.00 AS Decimal(10, 2)), 85, N'LSP04', N'Trà Sữa Thái Xanh.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP68', N'Nước Ép Cam', CAST(27000.00 AS Decimal(10, 2)), 90, N'LSP02', N'Nước Ép Cam.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP69', N'Sinh Tố Mâm Xôi', CAST(31000.00 AS Decimal(10, 2)), 50, N'LSP03', N'Sinh Tố Mâm Xôi.jpg')
INSERT [dbo].[SanPham] ([maSanPham], [tenSanPham], [giaBan], [soLuong], [maLoaiSanPham], [hinhAnh]) VALUES (N'SP70', N'Bánh Plan Caramel', CAST(23000.00 AS Decimal(10, 2)), 100, N'LSP06', N'Bánh Plan Caramel.jpg')
GO


INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [tenNhaCungCap], [diaChi], [soDienThoai]) VALUES (N'NCC001', N'Công Ty Nguyên Liệu Việt', N'123 Lê Lơi, Quận 1, TP.HCM', N'0901234567')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [tenNhaCungCap], [diaChi], [soDienThoai]) VALUES (N'NCC002', N'Nguyên Liệu Pha Chế ABC', N'45 Nguyễn Trãi, Quận 5, TP.HCM', N'0908765432')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [tenNhaCungCap], [diaChi], [soDienThoai]) VALUES (N'NCC003', N'Cty Cung Cấp Cà Phê Nam', N'78 Lý Thuờng Kiệt, Quận 10, TP.HCM', N'0911122233')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [tenNhaCungCap], [diaChi], [soDienThoai]) VALUES (N'NCC004', N'Nguyên Liệu Trung Nguyên', N'15 Trường Sơn, Tân Bình, TP.HCM', N'0933444555')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [tenNhaCungCap], [diaChi], [soDienThoai]) VALUES (N'NCC005', N'Cung ứng Ðồ Uống Ðại Dương', N'99 Ðinh Bộ Lĩnh, Bình Thạnh, TP.HCM', N'0945566778')
GO

INSERT [dbo].[NguyenLieu] ([maNguyenLieu], [tenNguyenLieu], [donViTinh], [giaNhap], [ngayNhap], [ngayHetHan])
VALUES (N'NL001', N'Cà phê Arabica', N'kg', CAST(150000.00 AS Decimal(10, 2)), CAST(N'2025-04-01' AS DATE), CAST(N'2025-05-01' AS DATE));
INSERT [dbo].[NguyenLieu] ([maNguyenLieu], [tenNguyenLieu], [donViTinh], [giaNhap], [ngayNhap], [ngayHetHan])
VALUES (N'NL002', N'Cà phê Robusta', N'kg', CAST(150000.00 AS Decimal(10, 2)), CAST(N'2025-04-02' AS DATE), CAST(N'2025-05-02' AS DATE));
INSERT [dbo].[NguyenLieu] ([maNguyenLieu], [tenNguyenLieu], [donViTinh], [giaNhap], [ngayNhap], [ngayHetHan])
VALUES (N'NL003', N'Sữa đặc', N'lon', CAST(20000.00 AS Decimal(10, 2)), CAST(N'2025-04-03' AS DATE), CAST(N'2025-06-17' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL004', N'Đường cát trắng', N'kg', 18000.00, CAST(N'2025-04-04' AS DATE), CAST(N'2025-05-04' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL005', N'Đá viên', N'kg', 3000.00, CAST(N'2025-04-15' AS DATE), CAST(N'2025-05-05' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL006', N'Bột cacao', N'kg', 250000.00, CAST(N'2025-04-09' AS DATE), CAST(N'2025-05-06' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL007', N'Trà xanh matcha', N'gói', 75000.00, CAST(N'2025-04-05' AS DATE), CAST(N'2025-08-07' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL008', N'Bột kem béo', N'kg', 65000.00, CAST(N'2025-04-08' AS DATE), CAST(N'2025-05-08' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL009', N'Trà ô long', N'gói', 80000.00, CAST(N'2025-04-09' AS DATE), CAST(N'2025-05-09' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL010', N'Bột trà sữa', N'gói', 70000.00, CAST(N'2025-04-30' AS DATE), CAST(N'2025-05-10' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL011', N'Bột vani', N'hộp', 40000.00, CAST(N'2025-04-11' AS DATE), CAST(N'2025-05-11' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL012', N'Trà đen', N'gói', 60000.00, CAST(N'2025-04-22' AS DATE), CAST(N'2025-05-12' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL013', N'Syrup caramel', N'chai', 55000.00, CAST(N'2025-04-13' AS DATE), CAST(N'2025-05-13' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL014', N'Syrup hazelnut', N'chai', 60000.00, CAST(N'2025-04-14' AS DATE), CAST(N'2025-05-14' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL015', N'Syrup vanilla', N'chai', 58000.00, CAST(N'2025-04-02' AS DATE), CAST(N'2025-05-15' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL016', N'Syrup dâu', N'chai', 50000.00, CAST(N'2025-04-16' AS DATE), CAST(N'2025-05-16' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL017', N'Syrup việt quất', N'chai', 52000.00, CAST(N'2025-04-24' AS DATE), CAST(N'2025-05-17' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL018', N'Bột quế', N'hộp', 45000.00, CAST(N'2025-04-18' AS DATE), CAST(N'2025-07-18' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL019', N'Kem tươi', N'hộp', 40000.00, CAST(N'2025-04-19' AS DATE), CAST(N'2025-05-19' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL020', N'Chocolate đen', N'thanh', 30000.00, CAST(N'2025-04-20' AS DATE), CAST(N'2025-05-20' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL021', N'Bánh oreo', N'gói', 20000.00, CAST(N'2025-04-21' AS DATE), CAST(N'2025-05-21' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL022', N'Thạch trái cây', N'kg', 40000.00, CAST(N'2025-04-22' AS DATE), CAST(N'2025-05-22' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL023', N'Trân châu đen', N'kg', 35000.00, CAST(N'2025-04-23' AS DATE), CAST(N'2025-05-23' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL024', N'Trân châu trắng', N'kg', 37000.00, CAST(N'2025-04-24' AS DATE), CAST(N'2025-05-24' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL025', N'Sữa tươi không đường', N'lít', 28000.00, CAST(N'2025-04-25' AS DATE), CAST(N'2025-05-25' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL026', N'Sữa tươi có đường', N'lít', 27000.00, CAST(N'2025-04-26' AS DATE), CAST(N'2025-05-26' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL027', N'Sữa hạnh nhân', N'lít', 45000.00, CAST(N'2025-04-27' AS DATE), CAST(N'2025-05-27' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL028', N'Bột sắn dây', N'kg', 60000.00, CAST(N'2025-04-28' AS DATE), CAST(N'2025-05-28' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL029', N'Mật ong nguyên chất', N'chai', 90000.00, CAST(N'2025-04-29' AS DATE), CAST(N'2025-05-29' AS DATE));
INSERT [dbo].[NguyenLieu] VALUES (N'NL030', N'Nước cốt dừa', N'lon', 25000.00, CAST(N'2025-04-30' AS DATE), CAST(N'2025-05-10' AS DATE));

GO

INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL001')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL005')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL006')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL017')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL022')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC001', N'NL023')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL002')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL003')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL004')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL006')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL008')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL013')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL020')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL022')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC002', N'NL023')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL010')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL013')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL015')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL016')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL022')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL026')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC003', N'NL028')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL005')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL006')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL013')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL016')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL020')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL027')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL028')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC004', N'NL029')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL002')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL005')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL006')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL009')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL014')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL018')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL020')
INSERT [dbo].[ChiTietNhaCungCap] ([maNhaCungCap], [maNguyenLieu]) VALUES (N'NCC005', N'NL021')
GO




INSERT [dbo].[KhoNguyenLieu] ([maKho], [tenKho], [diaChi]) VALUES (N'K001', N'Kho Nguyên Liệu Quận 1', N'25 Nguyễn Thị Minh Khai, Quận 1, TP. Hồ Chí Minh')
INSERT [dbo].[KhoNguyenLieu] ([maKho], [tenKho], [diaChi]) VALUES (N'K002', N'Kho Nguyên Liệu Quận 7', N'102 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh')
INSERT [dbo].[KhoNguyenLieu] ([maKho], [tenKho], [diaChi]) VALUES (N'K003', N'Kho Nguyên Liệu Thủ Ðức', N'12A Võ Văn Ngân, TP. Thủ Ðức, TP. Hồ Chí Minh')
GO

INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL001', 130)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL002', 75)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL003', 95)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL004', 60)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL005', 120)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL006', 55)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL007', 100)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL008', 150)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL009', 110)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL010', 135)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL011', 55)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL012', 140)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL013', 30)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL014', 80)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL015', 70)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL016', 145)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL017', 90)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL018', 50)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL019', 115)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K001', N'NL020', 65)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL005', 110)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL006', 40)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL007', 130)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL008', 85)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL009', 115)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL010', 60)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL011', 145)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL012', 75)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL013', 95)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL014', 105)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL021', 100)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL022', 115)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL023', 140)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL024', 60)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL025', 150)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL026', 95)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL027', 125)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL028', 70)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL029', 35)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K002', N'NL030', 50)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL001', 85)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL002', 145)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL003', 95)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL004', 135)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL015', 110)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL016', 30)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL017', 55)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL018', 90)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL019', 60)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL020', 150)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL021', 100)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL022', 50)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL023', 75)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL024', 115)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL025', 80)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL026', 70)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL027', 130)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL028', 65)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL029', 125)
INSERT [dbo].[ChiTietKhoNguyenLieu] ([maKho], [maNguyenLieu], [soLuong]) VALUES (N'K003', N'NL030', 45)
GO

