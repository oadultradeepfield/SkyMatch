package com.oadultradeepfield.skymatch.domain.repository

interface ISolveRepository {
    suspend fun solve(imageByte: ByteArray)
}