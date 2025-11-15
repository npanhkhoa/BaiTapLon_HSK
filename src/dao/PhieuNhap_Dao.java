package dao;

import connectDB.ConnectDataBase;
import entity.KhoNguyenLieu;
import entity.NguyenLieu;
import entity.NhaCungCap;
import view.Manager.PhieuNhapFrame;
import view.Manager.SuaNguyenLieuDialog;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static connectDB.ConnectDataBase.getConnection;

public class PhieuNhap_Dao {
    private static final String SELECT_ALL_SQL =
            "SELECT nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.giaNhap, "
                    + "nl.ngayNhap, nl.ngayHetHan, "
                    + "knl.maKho, knl.tenKho, knl.diaChi AS diaChiKho, "
                    + "ctknl.soLuong, "
                    + "ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi AS diaChiNCC, ncc.soDienThoai "
                    + "FROM NguyenLieu nl "
                    + "LEFT JOIN ChiTietKhoNguyenLieu ctknl ON nl.maNguyenLieu = ctknl.maNguyenLieu "
                    + "LEFT JOIN KhoNguyenLieu knl ON ctknl.maKho = knl.maKho "
                    + "LEFT JOIN ChiTietNhaCungCap ctncc ON nl.maNguyenLieu = ctncc.maNguyenLieu "
                    + "LEFT JOIN NhaCungCap ncc ON ctncc.maNhaCungCap = ncc.maNhaCungCap";
    private Connection con;
    private KhoNguyenLieu kho;
    private NhaCungCap ncc;

    public List<NguyenLieu> printAllNguyenLieu() {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Xử lý ngày
                Date ngayNhap = rs.getDate("ngayNhap");
                LocalDate ngayNhapLD = (ngayNhap != null) ? ngayNhap.toLocalDate() : null;

                Date ngayHetHan = rs.getDate("ngayHetHan");
                LocalDate ngayHetHanLD = (ngayHetHan != null) ? ngayHetHan.toLocalDate() : null;

                // Xử lý Kho - kiểm tra null
                KhoNguyenLieu kho = null;
                String maKho = rs.getString("maKho");
                if (maKho != null && !maKho.isEmpty()) {
                    kho = new KhoNguyenLieu(
                            maKho,
                            rs.getString("tenKho"),
                            rs.getString("diaChiKho")
                    );
                }
                NhaCungCap ncc = null;
                String maNCC = rs.getString("maNhaCungCap");
                if (maNCC != null && !maNCC.isEmpty()) {
                    ncc = new NhaCungCap(
                            maNCC,
                            rs.getString("tenNhaCungCap"),
                            rs.getString("diaChiNCC"),
                            rs.getString("soDienThoai")
                    );
                }

                dsNguyenLieu.add(new NguyenLieu(
                        rs.getString("maNguyenLieu"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("donViTinh"),
                        rs.getDouble("giaNhap"),
                        ngayNhapLD,
                        ngayHetHanLD,
                        kho,  // có thể là null
                        rs.getInt("soLuong"),
                        ncc   // có thể là null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nguyên liệu: " + e.getMessage());
            e.printStackTrace();
        }
        return dsNguyenLieu;
    }

    public boolean addNguyenLieu(NguyenLieu nl) {
        if (nl == null || nl.getKhoNguyenLieu() == null) {
            throw new IllegalArgumentException("NguyenLieu hoặc KhoNguyenLieu không được null.");
        }

        String insertNL = "INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan) VALUES (?, ?, ?, ?, ?, ?)";
        String insertCTKho = "INSERT INTO ChiTietKhoNguyenLieu (maKho, maNguyenLieu, soLuong) VALUES (?, ?, ?)";
        String insertCTNCC = "INSERT INTO ChiTietNhaCungCap (maNhaCungCap, maNguyenLieu) VALUES (?, ?)";
        String checkKho = "SELECT COUNT(*) FROM KhoNguyenLieu WHERE maKho = ?";
        String insertKho = "INSERT INTO KhoNguyenLieu (maKho, tenKho, diaChi) VALUES (?, ?, ?)";
        String checkNCC = "SELECT COUNT(*) FROM NhaCungCap WHERE maNhaCungCap = ?";
        String insertNCC = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            KhoNguyenLieu kho = nl.getKhoNguyenLieu();
            NhaCungCap ncc = nl.getNhaCungCap();

            insertKho(con, kho);
            insertNhaCungCap(con, ncc, nl.getMaNguyenLieu());

            try (PreparedStatement stmt = con.prepareStatement(insertNL)) {
                stmt.setString(1, nl.getMaNguyenLieu());
                stmt.setString(2, nl.getTenNguyenLieu());
                stmt.setString(3, nl.getDonViTinh());
                stmt.setDouble(4, nl.getGiaNhap());
                stmt.setDate(5, Date.valueOf(nl.getNgayNhap()));
                stmt.setDate(6, Date.valueOf(nl.getNgayHetHan()));
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = con.prepareStatement(insertCTKho)) {
                stmt.setString(1, kho.getMaKho());
                stmt.setString(2, nl.getMaNguyenLieu());
                stmt.setInt(3, nl.getSoLuong());
                stmt.executeUpdate();
            }

            if (ncc != null) {
                try (PreparedStatement stmt = con.prepareStatement(insertCTNCC)) {
                    stmt.setString(1, ncc.getMaNhaCungCap());
                    stmt.setString(2, nl.getMaNguyenLieu());
                    stmt.executeUpdate();
                }
            }


            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nguyên liệu: " + e.getMessage());
            rollbackConnection(con);
            return false;
        }
    }

    public boolean addNguyenLieu1(NguyenLieu nl) {
        if (nl == null || nl.getKhoNguyenLieu() == null || nl.getNhaCungCap() == null) {
            throw new IllegalArgumentException("NguyenLieu, KhoNguyenLieu hoặc NhaCungCap không được null.");
        }

        String insertKho = "INSERT INTO KhoNguyenLieu (maKho, tenKho, diaChi) VALUES (?, ?, ?)";
        String checkKho = "SELECT COUNT(*) FROM KhoNguyenLieu WHERE maKho = ?";

        String insertNCC = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";
        String checkNCC = "SELECT COUNT(*) FROM NhaCungCap WHERE maNhaCungCap = ?";

        String insertNL = "INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, donViTinh, giaNhap, ngayNhap, ngayHetHan) VALUES (?, ?, ?, ?, ?, ?)";

        String insertCTKho = "INSERT INTO ChiTietKhoNguyenLieu (maKho, maNguyenLieu, soLuong) VALUES (?, ?, ?)";
        String insertCTNCC = "INSERT INTO ChiTietNhaCungCap (maNhaCungCap, maNguyenLieu) VALUES (?, ?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            KhoNguyenLieu kho = nl.getKhoNguyenLieu();
            NhaCungCap ncc = nl.getNhaCungCap();

            // 1. Thêm Kho nếu chưa tồn tại
            try (PreparedStatement checkStmt = con.prepareStatement(checkKho)) {
                checkStmt.setString(1, kho.getMaKho());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement stmt = con.prepareStatement(insertKho)) {
                        stmt.setString(1, kho.getMaKho());
                        stmt.setString(2, kho.getTenKho());
                        stmt.setString(3, kho.getDiaChi());
                        stmt.executeUpdate();
                    }
                }
            }

            // 2. Thêm Nhà Cung Cấp nếu chưa tồn tại
            try (PreparedStatement checkStmt = con.prepareStatement(checkNCC)) {
                checkStmt.setString(1, ncc.getMaNhaCungCap());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement stmt = con.prepareStatement(insertNCC)) {
                        stmt.setString(1, ncc.getMaNhaCungCap());
                        stmt.setString(2, ncc.getTenNhaCungCap());
                        stmt.setString(3, ncc.getDiaChi());
                        stmt.setString(4, ncc.getSoDienThoai());
                        stmt.executeUpdate();
                    }
                }
            }

            // 3. Thêm NguyenLieu
            try (PreparedStatement stmt = con.prepareStatement(insertNL)) {
                stmt.setString(1, nl.getMaNguyenLieu());
                stmt.setString(2, nl.getTenNguyenLieu());
                stmt.setString(3, nl.getDonViTinh());
                stmt.setDouble(4, nl.getGiaNhap());
                stmt.setDate(5, java.sql.Date.valueOf(nl.getNgayNhap()));
                stmt.setDate(6, java.sql.Date.valueOf(nl.getNgayHetHan()));
                stmt.executeUpdate();
            }

            // 4. Thêm vào ChiTietKhoNguyenLieu
            try (PreparedStatement stmt = con.prepareStatement(insertCTKho)) {
                stmt.setString(1, kho.getMaKho());
                stmt.setString(2, nl.getMaNguyenLieu());
                stmt.setInt(3, nl.getSoLuong());
                stmt.executeUpdate();
            }

            // 5. Thêm vào ChiTietNhaCungCap
            try (PreparedStatement stmt = con.prepareStatement(insertCTNCC)) {
                stmt.setString(1, ncc.getMaNhaCungCap());
                stmt.setString(2, nl.getMaNguyenLieu());
                stmt.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nguyên liệu: " + e.getMessage());
            return false;
        }
    }


    private void insertKho(Connection con, KhoNguyenLieu kho) throws SQLException {
        String checkKho = "SELECT COUNT(*) FROM KhoNguyenLieu WHERE maKho = ?";
        String insertKho = "INSERT INTO KhoNguyenLieu (maKho, tenKho, diaChi) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(checkKho)) {
            stmt.setString(1, kho.getMaKho());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement stmtInsertKho = con.prepareStatement(insertKho)) {
                        stmtInsertKho.setString(1, kho.getMaKho());
                        stmtInsertKho.setString(2, kho.getTenKho());
                        stmtInsertKho.setString(3, kho.getDiaChi());
                        stmtInsertKho.executeUpdate();
                    }
                }
            }
        }
    }

    private void insertNhaCungCap(Connection con, NhaCungCap ncc, String maNguyenLieu) throws SQLException {
        if (ncc != null) {
            String checkNCC = "SELECT COUNT(*) FROM NhaCungCap WHERE maNhaCungCap = ?";
            String insertCTNCC = "INSERT INTO ChiTietNhaCungCap (maNhaCungCap, maNguyenLieu) VALUES (?, ?)";

            try (PreparedStatement stmt = con.prepareStatement(checkNCC)) {
                stmt.setString(1, ncc.getMaNhaCungCap());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        String insertNCC = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, diaChi, soDienThoai) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement stmtInsertNCC = con.prepareStatement(insertNCC)) {
                            stmtInsertNCC.setString(1, ncc.getMaNhaCungCap());
                            stmtInsertNCC.setString(2, ncc.getTenNhaCungCap());
                            stmtInsertNCC.setString(3, ncc.getDiaChi());
                            stmtInsertNCC.setString(4, ncc.getSoDienThoai());
                            stmtInsertNCC.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement stmt = con.prepareStatement(insertCTNCC)) {
                stmt.setString(1, ncc.getMaNhaCungCap());
                stmt.setString(2, maNguyenLieu);
                stmt.executeUpdate();
            }
        }
    }

    public boolean deleteNguyenLieu(String maNguyenLieu) {
        String deleteCTKho = "DELETE FROM ChiTietKhoNguyenLieu WHERE maNguyenLieu = ?";
        String deleteCTNCC = "DELETE FROM ChiTietNhaCungCap WHERE maNguyenLieu = ?";
        String deleteNL = "DELETE FROM NguyenLieu WHERE maNguyenLieu = ?";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            // Delete from ChiTietKhoNguyenLieu
            try (PreparedStatement stmt = con.prepareStatement(deleteCTKho)) {
                stmt.setString(1, maNguyenLieu);
                stmt.executeUpdate();
            }

            // Delete from ChiTietNhaCungCap
            try (PreparedStatement stmt = con.prepareStatement(deleteCTNCC)) {
                stmt.setString(1, maNguyenLieu);
                stmt.executeUpdate();
            }

            // Delete from NguyenLieu
            try (PreparedStatement stmt = con.prepareStatement(deleteNL)) {
                stmt.setString(1, maNguyenLieu);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nguyên liệu: " + e.getMessage());
            rollbackConnection(con);
            return false;
        }
    }

    public List<NguyenLieu> timKiem(Date ngayNhap, Date ngayHetHan, String tenNguyenLieu) {
        List<NguyenLieu> dsNguyenLieu = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(SELECT_ALL_SQL);
        List<Object> params = new ArrayList<>();
        boolean hasCondition = false;

        Connection con = ConnectDataBase.getConnection();
        if(con == null){
            System.out.println("Lỗi kết nối cơ sở dữ liệu");
            return dsNguyenLieu;
        }

        if (ngayNhap != null) {
            sqlBuilder.append(hasCondition ? " AND" : " WHERE");
            sqlBuilder.append(" nl.ngayNhap = ?");
            params.add(new java.sql.Date(ngayNhap.getTime()));
            hasCondition = true;
        }

        if (ngayHetHan != null) {
            sqlBuilder.append(hasCondition ? " AND" : " WHERE");
            sqlBuilder.append(" nl.ngayHetHan = ?");
            params.add(new java.sql.Date(ngayHetHan.getTime()));
            hasCondition = true;
        }

        if (tenNguyenLieu != null && !tenNguyenLieu.trim().isEmpty()) {
            sqlBuilder.append(hasCondition ? " AND" : " WHERE");
            sqlBuilder.append(" nl.tenNguyenLieu LIKE ?");
            params.add("%" + tenNguyenLieu.trim() + "%");
        }

        try (PreparedStatement stmt = con.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Date ngayNhapResult = rs.getDate("ngayNhap");
                    LocalDate ngayNhapLD = (ngayNhapResult != null) ? ngayNhapResult.toLocalDate() : null;

                    Date ngayHetHanResult = rs.getDate("ngayHetHan");
                    LocalDate ngayHetHanLD = (ngayHetHanResult != null) ? ngayHetHanResult.toLocalDate() : null;

                    String maKho = rs.getString("maKho");
                    KhoNguyenLieu kho = null;
                    if (maKho != null && !maKho.trim().isEmpty()) {
                        kho = new KhoNguyenLieu(
                                maKho,
                                rs.getString("tenKho"),
                                rs.getString("diaChiKho")
                        );
                    }

                    String maNCC = rs.getString("maNhaCungCap");
                    NhaCungCap ncc = null;
                    if (maNCC != null && !maNCC.trim().isEmpty()) {
                        ncc = new NhaCungCap(
                                maNCC,
                                rs.getString("tenNhaCungCap"),
                                rs.getString("diaChiNCC"),
                                rs.getString("soDienThoai")
                        );
                    }

                    dsNguyenLieu.add(new NguyenLieu(
                            rs.getString("maNguyenLieu"),
                            rs.getString("tenNguyenLieu"),
                            rs.getString("donViTinh"),
                            rs.getDouble("giaNhap"),
                            ngayNhapLD,
                            ngayHetHanLD,
                            kho,
                            rs.getInt("soLuong"),
                            ncc
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nguyên liệu: " + e.getMessage());
        }

        return dsNguyenLieu;
    }



    private void rollbackConnection(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi rollback: " + e.getMessage());
        }
    }

    public boolean suaNguyenLieu(NguyenLieu nl) {
        String sqlKhoNguyenLieu = "UPDATE KhoNguyenLieu SET tenKho = ?, diaChi = ? WHERE maKho = ?";
        String sqlChiTietKhoNguyenLieu = "UPDATE ChiTietKhoNguyenLieu SET soLuong = ? WHERE maKho = ? AND maNguyenLieu = ?";
        String sqlNguyenLieu = "UPDATE NguyenLieu SET tenNguyenLieu = ?, donViTinh = ?, giaNhap = ?, ngayNhap = ?, ngayHetHan = ? WHERE maNguyenLieu = ?";
        String sqlNhaCungCap = "UPDATE NhaCungCap SET tenNhaCungCap = ?, diaChi = ?, soDienThoai = ? WHERE maNhaCungCap = ?";

        Connection con = getConnection();

        try{
            con.setAutoCommit(false);

            try(PreparedStatement ps = con.prepareStatement(sqlKhoNguyenLieu)){
                ps.setString(1, nl.getKhoNguyenLieu().getTenKho());
                ps.setString(2, nl.getKhoNguyenLieu().getDiaChi());
                ps.setString(3, nl.getKhoNguyenLieu().getMaKho());
                ps.executeUpdate();
            }

            try( PreparedStatement ps = con.prepareStatement(sqlChiTietKhoNguyenLieu)){
                ps.setInt(1, nl.getSoLuong());
                ps.setString(2, nl.getKhoNguyenLieu().getMaKho());
                ps.setString(3, nl.getMaNguyenLieu());
                ps.executeUpdate();
            }

            try(PreparedStatement ps = con.prepareStatement(sqlNguyenLieu)){
                ps.setString(1, nl.getTenNguyenLieu());
                ps.setString(2, nl.getDonViTinh());
                ps.setDouble(3, nl.getGiaNhap());
                ps.setDate(4, Date.valueOf(nl.getNgayNhap()));
                ps.setDate(5, Date.valueOf(nl.getNgayHetHan()));
                ps.setString(6, nl.getMaNguyenLieu());
                ps.executeUpdate();
            }

            try(PreparedStatement ps = con.prepareStatement(sqlNhaCungCap)){
                ps.setString(1, nl.getNhaCungCap().getTenNhaCungCap());
                ps.setString(2, nl.getNhaCungCap().getDiaChi());
                ps.setString(3, nl.getNhaCungCap().getSoDienThoai());
                ps.setString(4, nl.getNhaCungCap().getMaNhaCungCap());
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }finally {
            try{
                con.setAutoCommit(true);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public NguyenLieu timByManguyenLieuVaMaKho(String maNguyenLieu, String maKho){
        NguyenLieu nguyenLieu = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            String sql = "SELECT nl.*, ctknl.soLuong, k.maKho, k.tenKho, k.diaChi AS diaChiKho, " +
                                 "ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi AS diaChiNCC, ncc.soDienThoai " +
                                 "FROM NguyenLieu nl " +
                                 "JOIN ChiTietKhoNguyenLieu ctknl ON nl.maNguyenLieu = ctknl.maNguyenLieu " +
                                 "JOIN KhoNguyenLieu k ON ctknl.maKho = k.maKho " +
                                 "JOIN ChiTietNhaCungCap ctncc ON ctknl.maNguyenLieu = ctncc.maNguyenLieu " +
                                 "JOIN NhaCungCap ncc ON ctncc.maNhaCungCap = ncc.maNhaCungCap " +
                                 "WHERE nl.maNguyenLieu = ? AND k.maKho = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNguyenLieu);
            stmt.setString(2, maKho);
            rs = stmt.executeQuery();

            if(rs.next()){
                KhoNguyenLieu kho = new KhoNguyenLieu(
                        rs.getString("maKho"),
                        rs.getString("tenKho"),
                        rs.getString("diaChiKho")
                );

                NhaCungCap ncc = new NhaCungCap(
                        rs.getString("maNhaCungCap"),
                        rs.getString("tenNhaCungCap"),
                        rs.getString("diaChiNCC"),
                        rs.getString("soDienThoai")
                );

                nguyenLieu = new NguyenLieu(
                        rs.getString("maNguyenLieu"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("donViTinh"),
                        rs.getDouble("giaNhap"),
                        rs.getDate("ngayNhap").toLocalDate(),
                        rs.getDate("ngayHetHan").toLocalDate(),
                        kho,
                        rs.getInt("soLuong"),
                        ncc
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return nguyenLieu;
    }
}