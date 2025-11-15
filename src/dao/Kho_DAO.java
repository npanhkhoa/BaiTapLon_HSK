package dao;/*
 * @ (#) Kho_DAO.java   1.0     30/04/2025
package dao;


/**
 * @description :
 * @author : Vy, Pham Kha Vy
 * @version 1.0
 * @created : 30/04/2025
 */

import connectDB.ConnectDataBase;
import entity.KhoViewTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Kho_DAO {
    public List<KhoViewTable> getDsachKhoNguyenLieu() {
        List<KhoViewTable> dsachKhoNguyenLieu = new ArrayList<>();
        try {
            String sql = """
                    select k.maKho,k.tenKho,k.diaChi, nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, ctk.soLuong, nl.ngayNhap, nl.ngayHetHan, ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi as diaChiNhaCungCap, ncc.soDienThoai
                    FROM KhoNguyenLieu k
                    LEFT JOIN ChiTietKhoNguyenLieu ctk ON k.maKho = ctk.maKho
                    LEFT JOIN NguyenLieu nl ON ctk.maNguyenLieu = nl.maNguyenLieu
                    LEFT JOIN ChiTietNhaCungCap ctncc ON nl.maNguyenLieu = ctncc.maNguyenLieu
                    LEFT JOIN NhaCungCap ncc ON ctncc.maNhaCungCap = ncc.maNhaCungCap
                    """;
            ConnectDataBase.getConnection();
            Statement stmt = ConnectDataBase.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maKho = rs.getString("maKho");
                String tenKho = rs.getString("tenKho");
                String diaChi = rs.getString("diaChi");
                String maNguyenLieu = rs.getString("maNguyenLieu");
                String tenNguyenLieu = rs.getString("tenNguyenLieu");
                String donViTinh = rs.getString("donViTinh");
                double giaNhap = rs.getDouble("giaNhap");
                int soLuong = rs.getInt("soLuong");
                Date ngayNhap = rs.getDate("ngayNhap");
                Date ngayHetHan = rs.getDate("ngayHetHan");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String diaChiNhaCungCap = rs.getString("diaChiNhaCungCap");
                String soDienThoaiNhaCungCap = rs.getString("soDienThoai");

                KhoViewTable khoNguyenLieu = new KhoViewTable(maKho, tenKho, diaChi, maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, soLuong, ngayNhap, ngayHetHan, maNhaCungCap, tenNhaCungCap, diaChiNhaCungCap, soDienThoaiNhaCungCap);
                dsachKhoNguyenLieu.add(khoNguyenLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsachKhoNguyenLieu;
    }

    public List<KhoViewTable> getAllKhoNguyenLieu() {
        List<KhoViewTable> list = new ArrayList<>();

        String sql = """
                    SELECT k.maKho, nl.maNguyenLieu,nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap,ctk.soLuong, nl.ngayNhap, nl.ngayHetHan, ncc.maNhaCungCap, ncc.tenNhaCungCap
               from KhoNguyenLieu k
               join ChiTietKhoNguyenLieu ctk on k.maKho = ctk.maKho
               join NguyenLieu nl on ctk.maNguyenLieu = nl.maNguyenLieu
               join ChiTietNhaCungCap ctncc on nl.maNguyenLieu = ctncc.maNguyenLieu
               join NhaCungCap ncc on ctncc.maNhaCungCap = ncc.maNhaCungCap
                """;

            try{
            Connection con = ConnectDataBase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String maKho = rs.getString("maKho");
                String maNguyenLieu = rs.getString("maNguyenLieu");
                String tenNguyenLieu = rs.getString("tenNguyenLieu");
                String donViTinh = rs.getString("donViTinh");
                double giaNhap = rs.getDouble("giaNhap");
                int soLuong = rs.getInt("soLuong");
                Date ngayNhap = rs.getDate("ngayNhap");
                Date ngayHetHan = rs.getDate("ngayHetHan");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");

                KhoViewTable viewKho = new KhoViewTable(maKho, maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap,soLuong, ngayNhap, ngayHetHan, maNhaCungCap, tenNhaCungCap);
                list.add(viewKho);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<KhoViewTable> getKhoChiTietByMaKho(String maKho, String maNguyenLieu ){
        List<KhoViewTable> list = new ArrayList<>();

        try{
            String sql = """
                    select k.maKho,k.tenKho, k.diaChi, nl.maNguyenLieu,nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap,ctk.soLuong, nl.ngayNhap, nl.ngayHetHan, ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi as diaChiNhaCungCap,ncc.soDienThoai
                    from KhoNguyenLieu k
                    join ChiTietKhoNguyenLieu ctk on k.maKho = ctk.maKho
                    join NguyenLieu nl on ctk.maNguyenLieu = nl.maNguyenLieu
                    join ChiTietNhaCungCap ctncc on nl.maNguyenLieu = ctncc.maNguyenLieu
                    join NhaCungCap ncc on ctncc.maNhaCungCap = ncc.maNhaCungCap
                    where k.maKho = ? and nl.maNguyenLieu = ?
                    """;

            Connection con = ConnectDataBase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKho);
            ps.setString(2, maNguyenLieu);

            ResultSet rs = ps.executeQuery();

            while (rs.next() ){
                String tenKho = rs.getString("tenKho");
                String diaChiKho = rs.getString("diaChi");
                String tenNguyenLieu = rs.getString("tenNguyenLieu");
                String donViTinh = rs.getString("donViTinh");
                double giaNhap = rs.getDouble("giaNhap");
                int soLuong = rs.getInt("soLuong");
                Date ngayNhap = rs.getDate("ngayNhap");
                Date ngayHetHan = rs.getDate("ngayHetHan");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String diaChiNhaCungCap = rs.getString("diaChiNhaCungCap");
                String soDienThoaiNhaCungCap = rs.getString("soDienThoai");

                KhoViewTable viewKho = new KhoViewTable(maKho ,tenKho ,diaChiKho, maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, soLuong, ngayNhap, ngayHetHan, maNhaCungCap, tenNhaCungCap, diaChiNhaCungCap, soDienThoaiNhaCungCap);
                list.add(viewKho);
            } }catch (SQLException e){
            e.printStackTrace();
        }
        return  list;
    }

    public List<String> getAllKho(){
        List<String> khoList = new ArrayList<>();
        String sql = "select tenKho from KhoNguyenLieu";

        try{
            Connection con = ConnectDataBase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String tenKho = rs.getString("tenKho");
                khoList.add(tenKho);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return khoList;
    }

    public List<KhoViewTable> getKhoTheoTenKho(String tenKho){
        List<KhoViewTable> list = new ArrayList<>();

        String sql = """
                SELECT k.maKho, nl.maNguyenLieu,nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap,ctk.soLuong, nl.ngayNhap, nl.ngayHetHan, ncc.maNhaCungCap, ncc.tenNhaCungCap
                from KhoNguyenLieu k
                join ChiTietKhoNguyenLieu ctk on k.maKho = ctk.maKho
                join NguyenLieu nl on ctk.maNguyenLieu = nl.maNguyenLieu
                join ChiTietNhaCungCap ctncc on nl.maNguyenLieu = ctncc.maNguyenLieu
                join NhaCungCap ncc on ctncc.maNhaCungCap = ncc.maNhaCungCap
                where k.tenKho = ?
                """;
        try{
            Connection con = ConnectDataBase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tenKho);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String maKho = rs.getString("maKho");
                String maNguyenLieu = rs.getString("maNguyenLieu");
                String tenNguyenLieu = rs.getString("tenNguyenLieu");
                String donViTinh = rs.getString("donViTinh");
                double giaNhap = rs.getDouble("giaNhap");
                int soLuong = rs.getInt("soLuong");
                Date ngayNhap = rs.getDate("ngayNhap");
                Date ngayHetHan = rs.getDate("ngayHetHan");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");

                KhoViewTable viewKho = new KhoViewTable(maKho, maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, soLuong, ngayNhap, ngayHetHan, maNhaCungCap, tenNhaCungCap);
                list.add(viewKho);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
               return list;
    }

    public boolean themKho(KhoViewTable kho) {
        String sqlKhoNguyenLieu = "INSERT INTO KhoNguyenLieu (maKho, tenKho, diaChi) VALUES (?, ?, ?)";
        String sqlChiTietKhoNguyenLieu = "INSERT INTO ChiTietKhoNguyenLieu (maKho, maNguyenLieu, soLuong) VALUES (?, ?, ?)";
        String sqlNguyenLieu = "INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlNhaCungCap = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";
        String sqlChiTietNhaCungCap = "INSERT INTO ChiTietNhaCungCap (maNhaCungCap, maNguyenLieu) VALUES (?, ?)";

        Connection con = ConnectDataBase.getConnection();

        try {
            con.setAutoCommit(false);

            // Kiểm tra nếu maNguyenLieu đã tồn tại trong bảng NguyenLieu
            String checkNguyenLieuExist = "SELECT COUNT(*) FROM NguyenLieu WHERE maNguyenLieu = ?";
            try (PreparedStatement psCheck = con.prepareStatement(checkNguyenLieuExist)) {
                psCheck.setString(1, kho.getMaNguyenLieu());
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    // Nếu không tồn tại, thêm vào bảng NguyenLieu
                    try (PreparedStatement psNguyenLieu = con.prepareStatement(sqlNguyenLieu)) {
                        psNguyenLieu.setString(1, kho.getMaNguyenLieu());
                        psNguyenLieu.setString(2, kho.getTenNguyenLieu());
                        psNguyenLieu.setString(3, kho.getDonViTinh());
                        psNguyenLieu.setDouble(4, kho.getGiaNhap());
                        psNguyenLieu.setDate(5, kho.getNgayNhap());
                        psNguyenLieu.setDate(6, kho.getNgayHetHan());
                        psNguyenLieu.executeUpdate();
                    }
                }
            }

            // Chèn vào KhoNguyenLieu
            try (PreparedStatement psKhoNguyenLieu = con.prepareStatement(sqlKhoNguyenLieu)) {
                psKhoNguyenLieu.setString(1, kho.getMaKho());
                psKhoNguyenLieu.setString(2, kho.getTenKho());
                psKhoNguyenLieu.setString(3, kho.getDiaChiKho());
                psKhoNguyenLieu.executeUpdate();
            }

            // Chèn vào ChiTietKhoNguyenLieu
            try (PreparedStatement psChiTietKhoNguyenLieu = con.prepareStatement(sqlChiTietKhoNguyenLieu)) {
                psChiTietKhoNguyenLieu.setString(1, kho.getMaKho());
                psChiTietKhoNguyenLieu.setString(2, kho.getMaNguyenLieu());
                psChiTietKhoNguyenLieu.setInt(3, kho.getSoLuong());
                psChiTietKhoNguyenLieu.executeUpdate();
            }

            // Chèn vào NhaCungCap và ChiTietNhaCungCap
            try (PreparedStatement psNhaCungCap = con.prepareStatement(sqlNhaCungCap)) {
                psNhaCungCap.setString(1, kho.getMaNhaCungCap());
                psNhaCungCap.setString(2, kho.getTenNhaCungCap());
                psNhaCungCap.setString(3, kho.getDiaChiNhaCungCap());
                psNhaCungCap.setString(4, kho.getSoDienThoaiNhaCungCap());
                psNhaCungCap.executeUpdate();
            }

            try (PreparedStatement psChiTietNhaCungCap = con.prepareStatement(sqlChiTietNhaCungCap)) {
                psChiTietNhaCungCap.setString(1, kho.getMaNhaCungCap());
                psChiTietNhaCungCap.setString(2, kho.getMaNguyenLieu());
                psChiTietNhaCungCap.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true); // Đặt lại AutoCommit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean xoaKho(String maKho) {
        Connection con = ConnectDataBase.getConnection();

        try {
            con.setAutoCommit(false); // Bắt đầu transaction

            // 1. Lấy danh sách maNguyenLieu thuộc kho này
            List<String> danhSachNguyenLieu = new ArrayList<>();
            String sqlLayNguyenLieu = "SELECT maNguyenLieu FROM ChiTietKhoNguyenLieu WHERE maKho = ?";
            try (PreparedStatement stmt = con.prepareStatement(sqlLayNguyenLieu)) {
                stmt.setString(1, maKho);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    danhSachNguyenLieu.add(rs.getString("maNguyenLieu"));
                }
            }

            // 2. Xóa ChiTietNhaCungCap theo danh sách maNguyenLieu
            String sqlXoaCTNCC = "DELETE FROM ChiTietNhaCungCap WHERE maNguyenLieu = ?";
            try (PreparedStatement stmt = con.prepareStatement(sqlXoaCTNCC)) {
                for (String maNL : danhSachNguyenLieu) {
                    stmt.setString(1, maNL);
                    stmt.executeUpdate();
                }
            }

            // 3. Xóa ChiTietKhoNguyenLieu theo maKho
            String sqlXoaCTKho = "DELETE FROM ChiTietKhoNguyenLieu WHERE maKho = ?";
            try (PreparedStatement stmt = con.prepareStatement(sqlXoaCTKho)) {
                stmt.setString(1, maKho);
                stmt.executeUpdate();
            }

            // 4. Xóa NguyenLieu nếu không còn trong kho nào khác
            String sqlXoaNL = """
            DELETE FROM NguyenLieu 
            WHERE maNguyenLieu NOT IN (
                SELECT maNguyenLieu FROM ChiTietKhoNguyenLieu
            )
        """;
            try (PreparedStatement stmt = con.prepareStatement(sqlXoaNL)) {
                stmt.executeUpdate();
            }

            // 5. Xóa kho
            String sqlXoaKho = "DELETE FROM KhoNguyenLieu WHERE maKho = ?";
            try (PreparedStatement stmt = con.prepareStatement(sqlXoaKho)) {
                stmt.setString(1, maKho);
                int rows = stmt.executeUpdate();

                if (rows == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public boolean suaKho(KhoViewTable kho) {
        String sqlUpdateKhoNguyenLieu = "UPDATE KhoNguyenLieu SET tenKho = ?, diaChi = ? WHERE maKho = ?";
        String sqlUpdateChiTietKhoNguyenLieu = "UPDATE ChiTietKhoNguyenLieu SET soLuong = ?, maNguyenLieu = ? WHERE maKho = ? AND maNguyenLieu = ?";
        String sqlUpdateNguyenLieu = "UPDATE NguyenLieu SET tenNguyenLieu = ?, donViTinh = ?, giaNhap = ?, ngayNhap = ?, ngayHetHan = ? WHERE maNguyenLieu = ?";
        String sqlUpdateNhaCungCap = "UPDATE NhaCungCap SET tenNhaCungCap = ?, diaChi = ?, soDienThoai = ? WHERE maNhaCungCap = ?";

        Connection con = ConnectDataBase.getConnection();

        try {
            con.setAutoCommit(false);

            // Cập nhật KhoNguyenLieu
            try (PreparedStatement ps = con.prepareStatement(sqlUpdateKhoNguyenLieu)) {
                ps.setString(1, kho.getTenKho());
                ps.setString(2, kho.getDiaChiKho());
                ps.setString(3, kho.getMaKho());
                ps.executeUpdate();
            }

            // Cập nhật ChiTietKhoNguyenLieu
            try (PreparedStatement ps = con.prepareStatement(sqlUpdateChiTietKhoNguyenLieu)) {
                ps.setInt(1, kho.getSoLuong());
                ps.setString(2, kho.getMaNguyenLieu());
                ps.setString(3, kho.getMaKho());
                ps.setString(4, kho.getMaNguyenLieu());
                ps.executeUpdate();
            }

            // Cập nhật NguyenLieu
            try (PreparedStatement ps = con.prepareStatement(sqlUpdateNguyenLieu)) {
                ps.setString(1, kho.getTenNguyenLieu());
                ps.setString(2, kho.getDonViTinh());
                ps.setDouble(3, kho.getGiaNhap());
                ps.setDate(4, kho.getNgayNhap());
                ps.setDate(5, kho.getNgayHetHan());
                ps.setString(6, kho.getMaNguyenLieu());
                ps.executeUpdate();
            }

            // Cập nhật NhaCungCap
            try (PreparedStatement ps = con.prepareStatement(sqlUpdateNhaCungCap)) {
                ps.setString(1, kho.getTenNhaCungCap());
                ps.setString(2, kho.getDiaChiNhaCungCap());
                ps.setString(3, kho.getSoDienThoaiNhaCungCap());
                ps.setString(4, kho.getMaNhaCungCap());
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public KhoViewTable getKhoByMaKhoViewTable(String maKho, String maNguyenLieu) {
        KhoViewTable kho = null;
        String sql = """
               
                SELECT k.maKho,k.tenKho,k.diaChi, nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, ctk.soLuong, nl.ngayNhap, nl.ngayHetHan, ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi as diaChiNhaCungCap, ncc.soDienThoai
               FROM KhoNguyenLieu k
               JOIN ChiTietKhoNguyenLieu ctk ON k.maKho = ctk.maKho
               JOIN NguyenLieu nl ON ctk.maNguyenLieu = nl.maNguyenLieu
               JOIN ChiTietNhaCungCap ctncc ON nl.maNguyenLieu = ctncc.maNguyenLieu
               JOIN NhaCungCap ncc ON ctncc.maNhaCungCap = ncc.maNhaCungCap
               WHERE k.maKho = ? and nl.maNguyenLieu = ?; 
               
                """;

        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKho);
            stmt.setString(2, maNguyenLieu);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Gán dữ liệu từ ResultSet vào đối tượng KhoViewTable
                    kho = new KhoViewTable();
                    kho.setMaKho(rs.getString("maKho"));
                    kho.setTenKho(rs.getString("tenKho"));
                    kho.setDiaChiKho(rs.getString("diaChi"));
                    kho.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    kho.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
                    kho.setDonViTinh(rs.getString("donViTinh"));
                    kho.setSoLuong(rs.getInt("soLuong"));
                    kho.setGiaNhap(rs.getDouble("giaNhap"));
                    kho.setNgayNhap(rs.getDate("ngayNhap"));
                    kho.setNgayHetHan(rs.getDate("ngayHetHan"));
                    kho.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                    kho.setTenNhaCungCap(rs.getString("tenNhaCungCap"));
                    kho.setDiaChiNhaCungCap(rs.getString("diaChiNhaCungCap"));
                    kho.setSoDienThoaiNhaCungCap(rs.getString("soDienThoai"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kho;
    }


    public boolean isMaKhoExists(String maKho) {
        String sql = "SELECT COUNT(*) FROM KhoNguyenLieu WHERE maKho = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKho);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã kho đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return false;
    }

    public boolean isMaNguyenLieuExists(String maNguyenLieu) {
        String sql = "SELECT COUNT(*) FROM NguyenLieu WHERE maNguyenLieu = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNguyenLieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã nguyên liệu đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isMaNhaCungCapExists(String maNhaCungCap) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE maNhaCungCap = ?";
        try (Connection conn = ConnectDataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNhaCungCap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã nhà cung cấp đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

