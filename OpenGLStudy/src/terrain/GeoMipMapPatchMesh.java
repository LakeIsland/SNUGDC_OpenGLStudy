package terrain;

import util.Loader;

public class GeoMipMapPatchMesh {

	private int MAP_BLOCK_SIZE = MapConstants.MAP_BLOCK_SIZE;
	private int MAX_LOD_LEVEL = MapConstants.MAX_LOD_LEVEL;

	private int vaoID;
	private int[][] iboIDs;
	private int[][] iboLengths;

	private int mostCoarseIbo;
	private int mostCoarseIboCount;
	
	public GeoMipMapPatchMesh(int MAP_BLOCK_SIZE, int MAX_LOD_LEVEL){
		this.MAP_BLOCK_SIZE = MAP_BLOCK_SIZE;
		this.MAX_LOD_LEVEL = MAX_LOD_LEVEL;
		
		registerVAO();
		registerIboIDs();
	}

	// register map block grid VAO
	private void registerVAO() {
		int count = (MAP_BLOCK_SIZE + 1) * (MAP_BLOCK_SIZE + 1);
		float[] gridVertices = new float[count * 2];
		int pointer = 0;
		for (int i = 0; i <= MAP_BLOCK_SIZE; i++) {
			for (int j = 0; j <= MAP_BLOCK_SIZE; j++) {
				gridVertices[pointer++] = j;
				gridVertices[pointer++] = i;
			}
		}
		vaoID = Loader.loadPos(gridVertices, 2);
	}

	// register Ibo Ids for each LOD levels.
	private void registerIboIDs() {
		iboIDs = new int[MAX_LOD_LEVEL][9];
		iboLengths = new int[MAX_LOD_LEVEL][3];

		for (int LOD = 0; LOD < MAX_LOD_LEVEL; LOD++) {
			int on = MAP_BLOCK_SIZE + 1;
			int n = (MAP_BLOCK_SIZE >> LOD) + 1;
			int[] indices_center = new int[6 * (n - 3) * (n - 3)];
			iboLengths[LOD][2] = 6 * (n - 3) * (n - 3);

			int unit = (1 << LOD);
			int pointer = 0;
			for (int gz = unit; gz < on - 2 * unit; gz += unit) {
				for (int gx = unit; gx < on - 2 * unit; gx += unit) {
					int topLeft = (gz * on) + gx;
					int topRight = topLeft + unit;
					int bottomLeft = ((gz + unit) * on) + gx;
					int bottomRight = bottomLeft + unit;
					indices_center[pointer++] = topLeft;
					indices_center[pointer++] = bottomLeft;
					indices_center[pointer++] = topRight;
					indices_center[pointer++] = topRight;
					indices_center[pointer++] = bottomLeft;
					indices_center[pointer++] = bottomRight;
				}
			}

			int[] indices_left_0 = new int[6 * (n - 2)];
			int[] indices_left_1 = new int[3 * (3 * n - 7) / 2];

			iboLengths[LOD][1] = indices_left_0.length;
			iboLengths[LOD][0] = indices_left_1.length;

			int pointer0 = 0;
			int pointer1 = 0;
			for (int gx = unit; gx < on - unit; gx += unit) {

				// 0 - 1 - 2
				// \ | /
				// \ | /
				// 3

				if ((gx / unit) % 2 == 1) {
					int index_0 = gx - unit;
					int index_1 = gx;
					int index_2 = gx + unit;
					int index_3 = gx + on * unit;

					indices_left_0[pointer0++] = index_0;
					indices_left_0[pointer0++] = index_3;
					indices_left_0[pointer0++] = index_1;
					indices_left_0[pointer0++] = index_1;
					indices_left_0[pointer0++] = index_3;
					indices_left_0[pointer0++] = index_2;

					indices_left_1[pointer1++] = index_0;
					indices_left_1[pointer1++] = index_3;
					indices_left_1[pointer1++] = index_2;
				}

				// 0
				// / | \
				// / | \
				// 1 - 2 - 3

				else {
					int index_0 = gx;
					int index_1 = gx + on * unit - unit;
					int index_2 = gx + on * unit;
					int index_3 = gx + on * unit + unit;
					indices_left_0[pointer0++] = index_0;
					indices_left_0[pointer0++] = index_1;
					indices_left_0[pointer0++] = index_2;
					indices_left_0[pointer0++] = index_0;
					indices_left_0[pointer0++] = index_2;
					indices_left_0[pointer0++] = index_3;

					indices_left_1[pointer1++] = index_0;
					indices_left_1[pointer1++] = index_1;
					indices_left_1[pointer1++] = index_2;
					indices_left_1[pointer1++] = index_0;
					indices_left_1[pointer1++] = index_2;
					indices_left_1[pointer1++] = index_3;
				}
			}

			int[] indices_top_0 = diagonalSymmetry(indices_left_0);
			int[] indices_top_1 = diagonalSymmetry(indices_left_1);
			int[] indices_right_0 = zSymmetry(indices_left_0);
			int[] indices_right_1 = zSymmetry(indices_left_1);
			int[] indices_bottom_0 = diagonalSymmetry(indices_right_0);
			int[] indices_bottom_1 = diagonalSymmetry(indices_right_1);

			iboIDs[LOD][0] = Loader.bindIndicesBuffer(indices_center);
			iboIDs[LOD][1] = Loader.bindIndicesBuffer(indices_left_0);
			iboIDs[LOD][2] = Loader.bindIndicesBuffer(indices_left_1);
			iboIDs[LOD][3] = Loader.bindIndicesBuffer(indices_top_0);
			iboIDs[LOD][4] = Loader.bindIndicesBuffer(indices_top_1);
			iboIDs[LOD][5] = Loader.bindIndicesBuffer(indices_right_0);
			iboIDs[LOD][6] = Loader.bindIndicesBuffer(indices_right_1);
			iboIDs[LOD][7] = Loader.bindIndicesBuffer(indices_bottom_0);
			iboIDs[LOD][8] = Loader.bindIndicesBuffer(indices_bottom_1);

		}

		int on = MAP_BLOCK_SIZE + 1;
		int n = (MAP_BLOCK_SIZE >> (MAX_LOD_LEVEL - 1)) + 1;
		int[] indices_center = new int[6 * (n - 1) * (n - 1)];
		int unit = (1 << (MAX_LOD_LEVEL));
		int pointer = 0;
		for (int gz = 0; gz < on - unit; gz += unit) {
			for (int gx = 0; gx < on - unit; gx += unit) {
				int topLeft = (gz * on) + gx;
				int topRight = topLeft + unit;
				int bottomLeft = ((gz + unit) * on) + gx;
				int bottomRight = bottomLeft + unit;
				indices_center[pointer++] = topLeft;
				indices_center[pointer++] = bottomLeft;
				indices_center[pointer++] = topRight;
				indices_center[pointer++] = topRight;
				indices_center[pointer++] = bottomLeft;
				indices_center[pointer++] = bottomRight;
			}
		}

		mostCoarseIbo = Loader.bindIndicesBuffer(indices_center);
		mostCoarseIboCount = 6 * (n - 1) * (n - 1);

	}

	private int[] diagonalSymmetry(int[] indices) {
		int[] result = new int[indices.length];
		int n = MAP_BLOCK_SIZE + 1;
		for (int i = 0; i < indices.length; i++) {
			result[i] = (indices[i] % n) * n + (indices[i] / n);
		}

		for (int i = 0; i < indices.length; i++) {
			if (i % 3 == 0) {
				int temp = result[i];
				result[i] = result[i + 1];
				result[i + 1] = temp;
			}
		}
		return result;
	}

	private int[] zSymmetry(int[] indices) {
		int[] result = new int[indices.length];
		int n = MAP_BLOCK_SIZE + 1;
		for (int i = 0; i < indices.length; i++) {
			result[i] = (n - 1 - (indices[i] / n)) * n + (indices[i] % n);
		}

		for (int i = 0; i < indices.length; i++) {
			if (i % 3 == 0) {
				int temp = result[i];
				result[i] = result[i + 1];
				result[i + 1] = temp;
			}
		}
		return result;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int[][] getIboIDs() {
		return iboIDs;
	}

	public int[][] getIboLengths() {
		return iboLengths;
	}

	public int getMostCoarseIbo() {
		return mostCoarseIbo;
	}

	public int getMostCoarseIboCount() {
		return mostCoarseIboCount;
	}
	
	
}
