package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dao.HoaDon_DAO;
import dao.SanPham_DAO;
import entity.HoaDon;
import entity.SanPham;

public class HoaDonController {
    private SanPham_DAO sanPhamDao = new SanPham_DAO();
    private SanPham sanPham = new SanPham();
    private HoaDon_DAO hoaDonDao = new HoaDon_DAO();
    private UserController userController = new UserController();


    public String getMaNhanVien(){
        return (userController.getNhanVienByTenDangNhap(userController.getCurrentUsername()).getMaNhanVien());
    }

    public String getTenDangNhap(){
        return userController.getCurrentUsername();
    }

    /**
     * Phương thức này sẽ tạo ra một mã hóa đơn ngẫu nhiên
     * và kiểm tra xem mã hóa đơn đó đã tồn tại trong cơ sở dữ liệu hay chưa.
     * Nếu đã tồn tại, nó sẽ tiếp tục tạo mã mới cho đến khi tìm thấy một mã chưa tồn tại.
     * Mã Hóa Đơn sẽ có định dạng "HDXXXX", trong đó XXXX là một số ngẫu nhiên từ 1000 đến 9999.
     * @return
     */
    public String generateMaHoaDon() {
        String maHoaDon;
        do {
            maHoaDon = "HD" + (1000 + new Random().nextInt(9000)); // Generate random ID in range HD1000-HD9999
        } while (hoaDonDao.isMaHoaDonExists(maHoaDon)); // Check if it already exists
        return maHoaDon;
    }


    /**
     * Phuong thuc nay insert Hoa Don len csdl
     * @param
     * @return
     */
    public boolean insertHoaDon(String maHoaDon, String maNhanVien, LocalDate ngayLap, double tongTien ,String maPTTT,String maKhachHang, 
		      String maKM, int trangThaiThanhToan , String ghiChu) {
        return HoaDon.insertHoaDon(maHoaDon, maNhanVien, ngayLap, tongTien);
    }

    /**
     * Phuong thuc nay insert chi tiet hoa don len csdl
     * @param
     * @return
     */
    public boolean insertChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, String maSanPham, int soLuong, BigDecimal giaBan, BigDecimal thanhTien) {
		return HoaDon.insertChiTietHoaDon(maChiTietHoaDon , maHoaDon , maSanPham , soLuong , giaBan , thanhTien);
        
    }

    /**
     * Phương thức này sẽ lấy danh sách hóa đơn của nhân viên dựa trên tên đăng nhập
     * @param tenDangNhap
     * @return
     */
    public List<HoaDon> getAllHoaDon(String tenDangNhap) {
        return hoaDonDao.getAllDsachHoaDon(tenDangNhap);
    }

    public String generateMaChiTietHoaDon() {
        String maChiTietHoaDon;
        do {
            maChiTietHoaDon = "CTHD" + (1000 + new Random().nextInt(9000)); // Generate random ID in range CTHD1000-CTHD9999
        } while (hoaDonDao.isMaHoaDonExists(maChiTietHoaDon)); // Check if it already exists
        return maChiTietHoaDon;
    }

    public Map<String, Integer> getTop8SanPhamBanChay(LocalTime startDate, LocalTime endDate) {
        return hoaDonDao.get8MonBanChayNhat(startDate, endDate);
    }

    public Map<LocalDate, Double> getDoanhThuTheoThoiGian(LocalTime startDate, LocalTime endDate) {
        return hoaDonDao.getDoanhThuTheoThoiGian(startDate, endDate);
    }

	public boolean insertHoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, BigDecimal tongTien) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getMatKhau() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isQuyenHan() {
		// TODO Auto-generated method stub
		return false;
	}



}