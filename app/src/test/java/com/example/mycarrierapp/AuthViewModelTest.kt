package com.example.mycarrierapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycarrierapp.data.AuthRepository
import com.example.mycarrierapp.data.FirebaseResource
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AuthRepository

    private lateinit var viewModel: AuthViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun testLoginSuccess() = TestScope().runTest {
        val email = "test@example.com"
        val password = "password"
        val user = mock(FirebaseUser::class.java)
        `when`(repository.login(email, password)).thenReturn(FirebaseResource.Success(user))

        val job = viewModel.login(email, password)
        job.join()

        assertEquals(FirebaseResource.Success(user), viewModel.loginFlow.value)
    }

    @Test
    fun testLoginFailure() = TestScope().runTest {
        val email = "test@example.com"
        val password = "password"
        val exception = Exception("Invalid credentials")
        `when`(repository.login(email, password)).thenReturn(FirebaseResource.Failure(exception))

        val job = viewModel.login(email, password)
        job.join()

        assertEquals(FirebaseResource.Failure(exception), viewModel.loginFlow.value)
    }

    @Test
    fun testSignupSuccess() = TestScope().runTest {
        val name = "John Doe"
        val email = "john.doe@example.com"
        val password = "password"
        val user = mock(FirebaseUser::class.java)
        `when`(repository.signup(name, email, password)).thenReturn(FirebaseResource.Success(user))

        viewModel.signup(name, email, password).join()

        assertEquals(FirebaseResource.Success(user), viewModel.signupFlow.value)
    }

    @Test
    fun testSignupFailure() = TestScope().runTest {
        val name = "John Doe"
        val email = "john.doe@example.com"
        val password = "password"
        val exception = Exception("Invalid credentials")
        `when`(repository.signup(name, email, password)).thenReturn(FirebaseResource.Failure(exception))

        viewModel.signup(name, email, password).join()

        assertEquals(FirebaseResource.Failure(exception), viewModel.signupFlow.value)
    }

    @Test
    fun testLogout() {
        viewModel.logout()

        assertNull(viewModel.loginFlow.value)
        assertNull(viewModel.signupFlow.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
