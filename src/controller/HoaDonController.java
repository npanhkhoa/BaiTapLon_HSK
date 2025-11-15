package controller;

import dao.HoaDon_Dao;
import dao.SanPham_Dao;
import entity.HoaDon;
import entity.SanPham;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HoaDonController {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private SanPham sanPham = new SanPham();
    private HoaDon_Dao hoaDonDao = new HoaDon_Dao();
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
    public boolean insertHoaDon(String maHoaDon, String maNhanVien, LocalDateTime ngayLap, double tongTien) {
        return hoaDonDao.insertHoaDon(maHoaDon, maNhanVien, ngayLap, tongTien);
    }

    /**
     * Phuong thuc nay insert chi tiet hoa don len csdl
     * @param
     * @return
     */
    public boolean insertChiTietHoaDon(String maChiTietHoaDon, String maHoaDon, String maSanPham, int soLuong, double giaBan, double thanhTien) {
        return hoaDonDao.insertChiTietHoaDon( maChiTietHoaDon, maHoaDon, maSanPham, soLuong, giaBan, thanhTien);
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
        } while (hoaDonDao.isMaChiTietHoaDonExists(maChiTietHoaDon)); // Check if it already exists
        return maChiTietHoaDon;
    }

    public Map<String, Integer> getTop8SanPhamBanChay(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonDao.get8MonBanChayNhat(startDate, endDate);
    }

    public Map<LocalDate, Double> getDoanhThuTheoThoiGian(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonDao.getDoanhThuTheoThoiGian(startDate, endDate);
    }

}
