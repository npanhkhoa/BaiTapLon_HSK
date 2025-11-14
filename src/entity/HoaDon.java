package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HoaDon {
	private String maHoaDon;
	private String maNhanVien;
	private LocalDate ngayLap;
	private double tongTien;
	private String maPTTT;
	private String maKhachHang;
	private String maKM;
	private int trangThaiThanhToan;
	private String ghiChu;
	
	private ArrayList<ChiTietHoaDon> dsChiTiet;
	private List<SanPham> dsachSanPham;
	
	public HoaDon() {
		super();
	}

// Constructor cho việc lấy hóa đơn từ CSDL
	public HoaDon(String maHoaDon, String maNhanVien, LocalDate ngayLap, 
				  double tongTien, String maPTTT,String maKhachHang, 
			      String maKM, int trangThaiThanhToan , String ghiChu) {
		
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
		this.maPTTT = maPTTT;
		this.maKhachHang = maKhachHang;
		this.maKM = maKM;
		this.trangThaiThanhToan = trangThaiThanhToan;
		this.ghiChu = ghiChu;
		this.dsChiTiet = new ArrayList<>();
		this.dsachSanPham = new List<SanPham>() {
			
			@Override
			public <T> T[] toArray(T[] a) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object[] toArray() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<SanPham> subList(int fromIndex, int toIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public SanPham set(int index, SanPham element) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean retainAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean removeAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public SanPham remove(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean remove(Object o) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ListIterator<SanPham> listIterator(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ListIterator<SanPham> listIterator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int lastIndexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Iterator<SanPham> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public int indexOf(Object o) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public SanPham get(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsAll(Collection<?> c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean contains(Object o) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean addAll(int index, Collection<? extends SanPham> c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean addAll(Collection<? extends SanPham> c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void add(int index, SanPham element) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean add(SanPham e) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
//Constructor cho việc tạo hóa đơn mới (chưa có mã)
	public HoaDon(String maNhanVien, LocalDate ngayLap, double tongTien, 
		          String maPTTT, String maKhachHang,
		          String maKM, int trangThaiThanhToan ,String ghiChu) {
	    this.maHoaDon = null; //chấp nhận null khi tạo 1 hoá đơn mới
	    this.maNhanVien = maNhanVien;
	    this.ngayLap = ngayLap;
	    this.tongTien = tongTien;
	    this.maPTTT = maPTTT;
	    this.maKhachHang = maKhachHang;
	    this.maKM = maKM;
	    this.trangThaiThanhToan = trangThaiThanhToan;
	    this.ghiChu = ghiChu;
	    this.dsChiTiet = new ArrayList<>();
}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		if (maHoaDon == null || maHoaDon.isEmpty())
			throw new IllegalArgumentException("Mã hóa đơn không được rỗng");

		if (!maHoaDon.matches("^HD\\d{3}$"))
			throw new IllegalArgumentException("Mã bàn phải có dạng Bxxx");

		this.maHoaDon = maHoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public LocalDate getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(LocalDate ngayLap) {
		this.ngayLap = ngayLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public String getMaPTTT() {
		return maPTTT;
	}

	public void setMaPTTT(String maPTTT) {
		this.maPTTT = maPTTT;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}

	public int getTrangThaiThanhToan() {
		return trangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(int trangThaiThanhToan) {
		this.trangThaiThanhToan = trangThaiThanhToan;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public ArrayList<ChiTietHoaDon> getDsChiTiet() {
		return dsChiTiet;
	}

	public void setDsChiTiet(ArrayList<ChiTietHoaDon> dsChiTiet) {
		this.dsChiTiet = dsChiTiet;
	}
	
	

	public List<SanPham> getDsachSanPham() {
		return dsachSanPham;
	}

	public void setDsachSanPham(List<SanPham> dsachSanPham) {
		this.dsachSanPham = dsachSanPham;
	}

	public static boolean insertHoaDon(String maHoaDon2, String maNhanVien2, LocalDate ngayLap2, double tongTien2) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean insertChiTietHoaDon(String maChiTietHoaDon, String maHoaDon2, String maSanPham,
			int soLuong, BigDecimal giaBan, BigDecimal thanhTien) {
		// TODO Auto-generated method stub
		return false;
	}



	public char[] getSoLuong() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getGiaBan() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	



	
	
	
	
	
	

	
	
	
	
	
}
